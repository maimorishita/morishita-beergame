//エンターキーを無効にする
document.onkeydown = KeyEvent;    
self.focus();                      

function KeyEvent(e){  
    pressKey=event.keyCode;  
    if(pressKey==13){return false;}
};