const cells = document.querySelectorAll(".cell");
const statusText = document.getElementById("status");
const restartBtn = document.getElementById("restartBtn");
const winSound = new Audio("win.mp3");

const scoreXText = document.getElementById("scoreX");
const scoreOText = document.getElementById("scoreO");

let scoreX = 0;
let scoreO = 0;

let board = ["", "", "", "", "", "", "", "", ""];
let currentPlayer = "X";
let gameActive = true;

const winPatterns = [
  [0, 1, 2],
  [3, 4, 5],
  [6, 7, 8],
  [0, 3, 6],
  [1, 4, 7],
  [2, 5, 8],
  [0, 4, 8],
  [2, 4, 6]
];

cells.forEach((cell) => {
  cell.addEventListener("click", handleCellClick);
});

restartBtn.addEventListener("click", restartGame);

function handleCellClick() {
  const index = this.getAttribute("data-index");

  if (board[index] !== "" || !gameActive) {
    return;
  }

  board[index] = currentPlayer;
  this.textContent = currentPlayer;

  checkWinner();

  if (gameActive) {
    currentPlayer = currentPlayer === "X" ? "O" : "X";
    statusText.textContent = `Player ${currentPlayer}'s turn`;
  }
}

function checkWinner() {
  let won = false;

  for (let pattern of winPatterns) {
    const [a, b, c] = pattern;

    if (board[a] !== "" && board[a] === board[b] && board[a] === board[c]) {
      won = true;
      break;
    }
  }

  if (won) {
    statusText.textContent = `Player ${currentPlayer} wins!`;
    gameActive = false;

    if (currentPlayer === "X") {
      scoreX++;
      scoreXText.textContent = scoreX;
    } else {
      scoreO++;
      scoreOText.textContent = scoreO;
    }

    return;
  }

  if (!board.includes("")) {
    statusText.textContent = "Game is a draw!";
    gameActive = false;
  }
}

function restartGame() {
  board = ["", "", "", "", "", "", "", "", ""];
  currentPlayer = "X";
  gameActive = true;
  statusText.textContent = "Player X's turn";

  cells.forEach((cell) => {
    cell.textContent = "";
  });
}