function getBytes(str) {
    let character;
    let charBytes = 0;
  
    for (let i = 0; i < str.length; i += 1) {
        character = str.charAt(i);
  
        if (escape(character).length > 4) {
            charBytes += 2;
        } else {
            charBytes += 1;
        }
    }
  
    return charBytes;
}


function fnSearch() {

    let name = $('#name').val();
    let exp = /[가-힣0-9a-zA-Z]+$/;

    if (name === '' || getBytes(name) < 4) {
        alert('이름은 최소 한글 2자 혹은 영문, 숫자 4자 이상 입력하셔야 합니다.');
        return;
    }

    if (!exp.test(name)) {
        alert('영어, 한글 및 숫자만 입력하실 수 있습니다.');
        return;
    }

    location.href = '/page/search?name=' + name;

    // axios({
    //     method : 'post',
    //     url : '/page/search',
    //     data : {name : $('#name').val()}
    // })
    // .then(response => {
    //     text = response.data['comment'];
    //     console.log(text);
    //     $('#txt').text(text);
    // })
    // .catch(error => {
    //     console.log('error : ' + error);
    // })
    // .finally(() => {
    //     console.log('axios end');
    // });
}