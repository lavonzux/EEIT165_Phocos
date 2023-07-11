const currentHref = window.location.href
// console.log(window.location);
const ContextPathname = currentHref.substring(0, currentHref.indexOf('phocos') + 6)
// console.log(ContextPathname);

const carouselCard = document.getElementById('carousel-figure')
const reserveBtn = document.getElementById('reserveBtn')

// reserveBtn.onclick(goToReserve())


const editButton = document.getElementById('edit-ps-service')
const deletePicBtns = document.querySelectorAll('.pictureDeleteBtn')
const updateBtn = document.getElementById('go-update-ps')

window.onload = function () {
    fetchServiceTypes()

    deletePicBtns.forEach(oneBtn => {
        oneBtn.addEventListener('click', (event) => addDeletePicID2Set(oneBtn))
    });

    updateBtn.addEventListener('click', (e) => pressedUpdateService())

}


function fetchServiceTypes(event) {
    // event.preventDefault
    const serviceTypeOptions = document.getElementById('serviceTypeOptions')

    axios.get(ContextPathname + '/serviceType/api/ReadAll')
        .then(res => {
            const serviceTypesList = res.data
            serviceTypesList.forEach(oneType => {
                const option = document.createElement('option')
                option.setAttribute('value', oneType.typeName)
                option.innerHTML = oneType.typeName
                serviceTypeOptions.appendChild(option)
                document.getElementById('serviceName').focus()
            });
        })
        .catch(err => {
            console.log();
        })
}




var picturesToDelete = new Set()
function addDeletePicID2Set(deleteBtn) {
    let pictureID = deleteBtn.dataset.existpictureid
    picturesToDelete.add(pictureID)
    console.log(pictureID + ' was added to delete pending set!');

    const toBeDltImg = document.querySelector('#carousel-in-modal-' + pictureID)
    console.log(toBeDltImg);
    toBeDltImg.classList.add('pendingForDelete')
}




function pressedUpdateService() {
    console.log('update Pressed');

    let form = document.getElementById('update-ps-service-form')
    let formDataObj = new FormData(form)
    console.log(formDataObj);

    let stringifyIDs = JSON.stringify([...picturesToDelete])

    sendUpdateService(formDataObj)
    goDeletePictures(picturesToDelete)
}


function goDeletePictures(pictureIDs) {
    axios({
        method: 'delete',
        url: ContextPathname + '/referencePicture/api/deleteMultiple',
        data: JSON.stringify([...pictureIDs])
    })
        .then(res => {
            console.log(res.data);
        })
        .catch(err => {
            console.log(err);
        })
}

function sendUpdateService(form) {
    console.log(form);
    axios({
        method: 'put',
        url: ContextPathname + '/photoService/api/Update',
        data: form
    })
        .then(res => {
            console.log(res.data);
            // location.reload()
        })
        .catch(err => {
            console.log(err);
        })
}








function toggleEditable(block) {
    if (block.contentEditable != true) {
        block.contentEditable = true
    } else {
        block.contentEditable = false
    }
}

function toggleBtnDisply(button) {
    console.log(button.style.display);
    if (button.style.display != 'none') {
        button.style.display = 'none'
    } else {
        button.style.display = 'block'
    }
}

