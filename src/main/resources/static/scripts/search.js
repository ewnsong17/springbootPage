function fnSearch() {

    let name = $('#name').val();
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