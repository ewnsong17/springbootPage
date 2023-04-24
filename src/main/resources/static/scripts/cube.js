let count = 0;
let potential = 1;

function fnMoveCube()
{
    location.href = '/page/cube';
}

function fnCubeInfo()
{
    switch (potential)
    {
        case 1:
            return "레어";
        case 2:
            return "에픽";
        case 3:
            return "유니크";
        case 4:
            return "레전드리";
    }
    return "";
}

function fnUseCube()
{
    axios
    .post('/page/useCube', null,
    {
        params: {
            itemID : 5062009
            , cubeCnt : count
            , cubePotential : potential
        }
    })
    .then(response => {
        cubeResult = response.data['cubeResult'];
        count = cubeResult['cubeCnt'];
        potential = cubeResult['cubePotential'];

        $('#cnt').text(count);

        $('#info').text(fnCubeInfo());
    })
    .catch(error => {
        console.log('error : ' + error);
    })
    .finally(() => {
        console.log('axios end.');
    });
}