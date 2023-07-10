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


function fetchServiceTypes() {
    // event.preventDefault
    const serviceTypeOptions = document.getElementById('serviceTypeOptions')
    const currentServiceType = serviceTypeOptions.dataset.servicetype

    axios.get(ContextPathname + '/serviceType/api/ReadAll')
        .then(res => {
            const serviceTypesList = res.data
            serviceTypesList.forEach((oneType, index) => {
                const option = document.createElement('option')
                option.setAttribute('value', oneType.typeName)
                if (oneType.typeName == currentServiceType) {
                    option.setAttribute('selected', 'true')
                }
                option.innerHTML = oneType.typeName
                serviceTypeOptions.appendChild(option)
                document.getElementById('serviceName').focus()
            });
        })
        .catch(err => {
            console.log(err);
        })
}




let picturesToDelete = new Array()
function addDeletePicID2Set(deleteBtn) {
    let pictureID = deleteBtn.dataset.existpictureid
    picturesToDelete.push(pictureID)
    console.log(pictureID + ' was added to delete pending set!');
    console.log(picturesToDelete + ' are pending for delete');

    const toBeDltImg = document.querySelector('#carousel-in-modal-' + pictureID)
    toBeDltImg.classList.add('pendingForDelete')
}

function cancelDeletePicSet() {
    picturesToDelete.length = 0
    document.querySelectorAll('.pendingForDelete').forEach(oneNode => { oneNode.classList.remove('pendingForDelete') })
}



function pressedUpdateService() {
    console.log('update Pressed');

    let form = document.getElementById('update-ps-service-form')
    let formDataObj = new FormData(form)
    formDataObj.append('picIDsToDelete', picturesToDelete)
    console.log(formDataObj);


    sendUpdateService(formDataObj)
    // goDeletePictures(picturesToDelete)
}


function goDeletePictures(pictureIDs) {
    if (pictureIDs.length > 0) {
        axios({
            method: 'delete',
            url: ContextPathname + '/referencePicture/api/deleteMultiple',
            data: pictureIDs
        })
            .then(res => {
                console.log(res.data);
            })
            .catch(err => {
                console.log(err);
            })
    }
}

function sendUpdateService(form) {
    console.log(form);
    axios({
        method: 'put',
        url: ContextPathname + '/photoService/api/Update',
        data: form
    })
        .then(res => {
            // console.log(res.data);
            // location.reload()
        })
        .catch(err => {
            console.log(err);
        })
}
