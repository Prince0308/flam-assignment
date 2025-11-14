// src/main.ts
const cam = document.getElementById('cam') as HTMLVideoElement;
const srcCanvas = document.getElementById('srcCanvas') as HTMLCanvasElement;
const outCanvas = document.getElementById('outCanvas') as HTMLCanvasElement;
const startCamBtn = document.getElementById('startCam') as HTMLButtonElement;
const fileInput = document.getElementById('file') as HTMLInputElement;
const toggleBtn = document.getElementById('toggleProc') as HTMLButtonElement;
const downloadLink = document.getElementById('download') as HTMLAnchorElement;

let streaming = false;
let processing = false;
let srcCtx = srcCanvas.getContext('2d')!;
let outCtx = outCanvas.getContext('2d')!;

function loadOpenCv(): Promise<void> {
  return new Promise((resolve, reject) => {
    const script = document.createElement("script");
    script.src = "https://docs.opencv.org/4.x/opencv.js";  // stable version
    script.async = true;
    script.onload = () => {
      // @ts-ignore
      cv['onRuntimeInitialized'] = () => {
        console.log("OpenCV.js is ready!");
        resolve();
      };
    };
    script.onerror = () => reject("Failed to load OpenCV.js");
    document.body.appendChild(script);
  });
}



async function start() {
  await loadOpenCv();
  console.log('OpenCV loaded', (window as any).cv);

  startCamBtn.onclick = startCamera;
  fileInput.onchange = handleFile;
  toggleBtn.onclick = () => { processing = !processing; toggleBtn.textContent = processing ? 'Stop Processing' : 'Start Processing'; if (processing) processLoop(); };
  downloadLink.onclick = () => { downloadLink.href = outCanvas.toDataURL('image/png'); };
}

async function startCamera() {
  try {
    const stream = await navigator.mediaDevices.getUserMedia({ video: { width: 640, height: 480 }, audio: false });
    cam.srcObject = stream;
    cam.onloadedmetadata = () => { cam.play(); streaming = true; };
  } catch (e) {
    alert('Camera access denied or not available');
    console.error(e);
  }
}

function handleFile() {
  const f = fileInput.files?.[0];
  if (!f) return;
  const img = new Image();
  img.onload = () => {
    srcCanvas.width = img.width;
    srcCanvas.height = img.height;
    outCanvas.width = img.width;
    outCanvas.height = img.height;
    srcCtx.drawImage(img, 0, 0);
  };
  img.src = URL.createObjectURL(f);
}

function captureFrame(): ImageData | null {
  if (streaming) {
    srcCtx.drawImage(cam, 0, 0, srcCanvas.width, srcCanvas.height);
    return srcCtx.getImageData(0, 0, srcCanvas.width, srcCanvas.height);
  }
  return srcCtx.getImageData(0, 0, srcCanvas.width, srcCanvas.height);
}

function processFrame(imageData: ImageData) {
  // Use OpenCV.js to convert to gray and run Canny
  // @ts-ignore
  const cv = (window as any).cv as typeof import('opencv-js');
  const src = cv.matFromImageData(imageData);
  const gray = new cv.Mat();
  cv.cvtColor(src, gray, cv.COLOR_RGBA2GRAY);
  const edges = new cv.Mat();
  cv.Canny(gray, edges, 75, 150);
  // convert edges to RGBA for canvas
  const rgba = new cv.Mat();
  cv.cvtColor(edges, rgba, cv.COLOR_GRAY2RGBA);
  const outImg = new ImageData(new Uint8ClampedArray(rgba.data), rgba.cols, rgba.rows);
  outCtx.putImageData(outImg, 0, 0);
  // cleanup
  src.delete(); gray.delete(); edges.delete(); rgba.delete();
}

async function processLoop() {
  while (processing) {
    const img = captureFrame();
    if (img) processFrame(img);
    // adjust FPS
    await new Promise(r => setTimeout(r, 30));
  }
}

start();
