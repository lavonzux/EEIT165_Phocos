const currentHref = window.location.href
// console.log(window.location);
const ContextPathname = currentHref.substring(0, currentHref.indexOf('phocos') + 6)
// console.log(ContextPathname);


const myBtn = document.querySelector('.my-customer-data-btn');
const output = document.querySelector('.output');




window.onload = function () {
    // grabAllPhotoServices();
    // grabRefPicImg(1003);

    // grabRefPicIds(1030);
    grabAllCarousel();
}

async function grabAllCarousel() {
    const carouselNodes = document.querySelectorAll('.carousel-card')

    carouselNodes.forEach(node => {
        let dataServiceID = node.dataset.serviceid
        let refPicsHtml = grabRefPicIds(dataServiceID);
    })
}




function grabAllPhotoServices() {
    const url = ContextPathname + '/photoService/api/ReadAll'
    return axios.get(url)
}

function grabRefPicIds(serviceID) {
    const url = ContextPathname + '/referencePicture/api/getPicIds?serviceID=' + serviceID
    return axios.get(url)
        .then(res => {
            let carouselhtml = carouselHtmlMaker(res.data, serviceID);
            console.log(carouselhtml);
        })
}


function carouselHtmlMaker(RefPicIds, serviceid) {
    const targetCarouselCard = document.querySelector(`[data-serviceid="${serviceid}"]`)

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
        let picImgElement = refPicImgHtmlMaker(pictureID)
        htmlStr += `
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
        </div>
    `
    targetCarouselCard.innerHTML = htmlStr
}


function refPicImgHtmlMaker(pictureID) {
    const url = ContextPathname + '/referencePicture/api/getPicById?pictureID=' + pictureID
    // const targetImg = document.getElementById('targetImg')
    // console.log('fetcing pic for' + pictureID + '......');
    return `<img class="d-block w-100 sclDwn" src="${url}" id="targetImg"/>`
}
