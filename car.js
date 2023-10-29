let gasolineLevel = 0; // Capacidade máxima: 120 litros
let speed = 0;
let engineOn = false;

function updateDisplay() {
  document.getElementById("gasoline-level").textContent = gasolineLevel;
  document.getElementById("speed").textContent = speed;
  document.getElementById("engine-status").textContent = engineOn ? "Ligado" : "Desligado";
}

function fillGasTank() {
  const pricePerLiter = parseFloat(prompt("Digite o preço da gasolina por litro:"));
  const liters = parseFloat(prompt("Quantos litros deseja abastecer?"));

  if (liters + gasolineLevel > 120) {
    alert("O tanque não suporta essa quantidade de gasolina!");
  } else {
    const cost = pricePerLiter * liters;
    gasolineLevel += liters;
    alert(`Abastecido ${liters} litros. Custo total: R$ ${cost.toFixed(2)}`);
    updateDisplay();
  }
}

function toggleEngine() {
  engineOn = !engineOn;
  updateDisplay();
}

function accelerate() {
  if (engineOn) {
    speed += 10;
    if (speed > 180) {
      speed = 180;
    }
    updateDisplay();
  } else {
    alert("Ligue o motor antes de acelerar.");
  }
}

function brake() {
  speed -= 10;
  if (speed < 0) {
    speed = 0;
  }
  updateDisplay();
}

document.getElementById("fillButton").addEventListener("click", fillGasTank);
document.getElementById("engineButton").addEventListener("click", toggleEngine);
document.getElementById("accelerateButton").addEventListener("click", accelerate);
document.getElementById("brakeButton").addEventListener("click", brake);

updateDisplay();
