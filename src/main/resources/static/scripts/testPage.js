function fnClick() {

    axios({
        method : 'get',
        url : '/page/get',
        data : {}
    })
    .then(response => {
        text = response.data['comment'];
        console.log(text);
        $('#txt').text(text);
    })
    .catch(error => {
        console.log('error : ' + error);
    })
    .finally(() => {
        console.log('axios end');
    });
}