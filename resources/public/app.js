let clickCount = 0;
const cup = document.querySelector('.coffee-cup');

cup.addEventListener('click', () => {
    clickCount ++;
    if (lickCount >= Math.floor(Math.random() * 9) + 1) {
        window.location.href = "/about";
    }
});