const currentHref = window.location.href
// console.log(window.location);
const ContextPathname = currentHref.substring(0, currentHref.indexOf('phocos') + 6)
// console.log(ContextPathname);



window.onload = function () {
    grabAllCarousel();
    fetchPopularTypes()
}

async function grabAllCarousel() {
    const carouselNodes = document.querySelectorAll('.carousel-card')

    carouselNodes.forEach(node => {
        let dataServiceID = node.dataset.serviceid
        let refPicsHtml = grabRefPicIds(dataServiceID);
    })
}


function grabRefPicIds(serviceID) {
    const url = ContextPathname + '/referencePicture/api/getPicIds?serviceID=' + serviceID
    return axios.get(url)
        .then(res => {
            carouselHtmlMaker(res.data, serviceID);
        })
        .catch(err => {
            console.log('Cannot find Reference Pictures for: ' + serviceID);
        })
}


function carouselHtmlMaker(RefPicIds, serviceid) {
    const targetCarouselCard = document.querySelector(`[data-serviceid="${serviceid}"]`)

    let htmlStr = `
        <div id="carousel-indicator-${serviceid}" class="carousel slide" data-ride="carousel">
            <div class="carousel-inner">
            <div class="carousel-indicators">
    `

    if (RefPicIds.length > 1) {

        RefPicIds.forEach((pictureID, index) => {
            if (index == 0) {
                htmlStr += `
                <button type="button" data-bs-target="#carousel-indicator-${serviceid}" data-bs-slide-to="${index}" class="active" aria-current="true" aria-label="Slide 1"></button>
            `
            } else {
                htmlStr += `
                <button type="button" data-bs-target="#carousel-indicator-${serviceid}" data-bs-slide-to="${index}" aria-label="Slide ${index + 1}"></button>
            `
            }
        })
    }

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

    if (RefPicIds.length > 1) {
        htmlStr += `
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#carousel-indicator-${serviceid}" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carousel-indicator-${serviceid}" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        `
    }
    targetCarouselCard.innerHTML = htmlStr
}


function refPicImgHtmlMaker(pictureID) {
    const url = ContextPathname + '/referencePicture/api/getPicById?pictureID=' + pictureID
    // const targetImg = document.getElementById('targetImg')
    // console.log('fetcing pic for' + pictureID + '......');
    return `<img class="d-block w-100 sclDwn" src="${url}" id="targetImg"/>`
}




function fetchPopularTypes() {
    const topTypes = []
    axios({
        method: 'get',
        url: ContextPathname + '/serviceType/api/popular'
    })
        .then(res => {
            console.log(res.data);
            makeTypeLink(res.data)
        })
        .catch(err => {
            console.log(err);
        })
}


function makeTypeLink(serviceTypes) {
    let serviceTypes1 = serviceTypes.slice(0, 3)
    console.log(serviceTypes1);
    let serviceTypes2 = serviceTypes.slice(3)
    console.log(serviceTypes2);

    const popularTypes1 = document.querySelector('#popularTypes1')
    const popularTypes2 = document.querySelector('#popularTypes2')
    serviceTypes1.forEach(ele => {
        let oneli = document.createElement('li')
        typeLink = `<a href="${ContextPathname}/photoService/ReadByType?serviceType=${ele.typeName}">${ele.typeName}</a>`
        oneli.innerHTML = typeLink
        popularTypes1.append(oneli)
    });

    serviceTypes2.forEach(ele => {
        let oneli = document.createElement('li')
        typeLink = `<a href="${ContextPathname}/photoService/ReadByType?serviceType=${ele.typeName}">${ele.typeName}</a>`
        oneli.innerHTML = typeLink
        popularTypes2.append(oneli)
    });

}
