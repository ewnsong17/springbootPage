let count = 0;
let potential = 1;

function fnMoveCube()
{
    location.href = '/page/cube';
}

function fnUseCube()
{
    axios
    .post('/page/useCube', null,
    {
        params: {
            cubeCnt : count
            , cubePotential : potential
        }
    })
    .then(response => {
        cubeResult = response.data['cubeResult'];
        count = cubeResult['cubeCnt'];
        potential = cubeResult['cubePotential'];

        $('#cnt').text(count);
    })
    .catch(error => {
        console.log('error : ' + error);
    })
    .finally(() => {
        console.log('axios end');
    });
}