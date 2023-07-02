const currentHref = window.location.href
// console.log(window.location);
const ContextPathname = currentHref.substring(0, currentHref.indexOf('phocos') + 6)
// console.log(ContextPathname);


const myBtn = document.querySelector('.my-customer-data-btn');
const output = document.querySelector('.output');




window.onload = function () {
    grabAllPhotoServices();
    // grabRefPicImg(1003);

    grabRefPicIds(1030);

}




function grabAllPhotoServices() {
    const url = ContextPathname + '/photoService/api/ReadAll'
    axios.get(url)
        .then(res => {
            const data = res.data
            data.forEach(entry => {
                console.log(entry);
            });
        })
        .catch(err => {
            console.log(err);
        })
}

function photoServiceCardMaker(photoService) {
    const output = document.getElementById('card-top')
    output.innerHTML += '<h5>回傳的 Data</h5>'
    photoService.forEach((el, index) => {
        const div = document.createElement('div');
        div.innerHTML += `<div> id: ${el.id}</div>`
        div.innerHTML += `<div> 會員姓名: ${el.name}</div>`
        div.innerHTML += `<div> 會員等級: ${el.level}</div><br>`
        output.append(div);
    })
}




function grabRefPicIds(serviceID) {
    const url = ContextPathname + '/referencePicture/api/getPicIds?serviceID=' + serviceID
    let data
    axios.get(url)
        .then(res => {
            console.log(res.data);
            data = res.data
            cardCarouselMaker(data)
        })
        .catch(err => {
            console.log(err);
        })
}


function cardCarouselMaker(RefPicIds) {
    const cardCarousel = document.getElementById('card-carousel')

    let htmlStr = `
        <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
            <div class="carousel-inner">
            <div class="carousel-indicators">
        `
    RefPicIds.forEach((pictureID, index) => {
        if (index == 0) {
            htmlStr += `
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="${index}" class="active" aria-current="true" aria-label="Slide 1"></button>
            `
        } else {
            htmlStr += `
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="${index}" aria-label="Slide ${index + 1}"></button>
            `
        }
    })
    htmlStr += `
            </div>
        `
    RefPicIds.forEach((pictureID, index) => {
        let picImgElement = grabRefPicImg(pictureID)
        htmlStr +=
            `
                <div class="p-1 carousel-item ${index == 0 ? 'active' : ''}">
                    ${picImgElement}
                </div>
        `
    });
    htmlStr += `
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>`
    cardCarousel.innerHTML = htmlStr
}


function grabRefPicImg(pictureID) {

    const url = ContextPathname + '/referencePicture/api/getPicById?pictureID=' + pictureID
    // const targetImg = document.getElementById('targetImg')
    console.log('fetcing pic for' + pictureID + '......');

    return `<img class="d-block w-100 sclDwn" src="${url}" id="targetImg"/>`
    /*
    axios.get(url)
    .then(res => {
        console.log(res);
        return `<img class="card-img-top" src="${url}" id="targetImg"/>`
    })
    .catch(err => {
        console.log(err);
        return `<img class="card-img-top" src="${ContextPathname + '0'}" id="targetImg">`
    })
*/
}
