import { Application, Sprite } from "https://cdnjs.cloudflare.com/ajax/libs/pixi.js/6.5.4/browser/pixi.min.mjs";

const screenWidth = 496;
const screenHeight = 377;

const app = new Application({
    backgroundColor : 0x000000,
    width : screenWidth, //window.innerWidth,
    height : screenHeight, //window.innerHeight,
});

const backgroundImage = Sprite.from("/images/map.png");
const characterImage = Sprite.from("/images/bird.png");
app.stage.addChild(backgroundImage);
app.stage.addChild(characterImage);

$(function() {
//    backgroundImage.position.set(-900, -900);
    app.start();

    console.log(backgroundImage);
    console.log(characterImage);

    $('#div').append(app.view);
});

$('#div').on("keydown", function(e) {
    const keyCode = e.keyCode;
    //37 : left, 38 : up, 39 : right, 40 : down
    if (keyCode >= 37 && keyCode <= 40) {
        const x = characterImage.position._x;
        const y = characterImage.position._y;

        const newX = Math.max(0, x + (keyCode == 37 ? -5 : keyCode == 39 ? 5 : 0));
        const newY = Math.max(0, y + (keyCode == 38 ? -5 : keyCode == 40 ? 5 : 0));

        characterImage.position.set(Math.min(newX, screenWidth), Math.min(newY, screenHeight));
    }

});