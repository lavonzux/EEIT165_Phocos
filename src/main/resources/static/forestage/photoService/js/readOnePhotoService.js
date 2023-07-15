// import Swal from 'sweetalert2'

const currentHref = window.location.href
const ContextPathname = currentHref.substring(0, currentHref.indexOf('phocos') + 6)


const deletePicBtns = document.querySelectorAll('.pictureDeleteBtn')
const updateBtn = document.getElementById('go-update-ps')
const deletePSBtn = document.getElementById('delete-ps-service')

const gotoCalBtn = document.getElementById('go-to-gcalendar')
const mailtoBtn = document.getElementById('send-mail')

window.onload = function () {
    fetchServiceTypes()

    updateBtn.addEventListener('click', (e) => pressedUpdateService())


    deletePicBtns.forEach(oneBtn => {
        oneBtn.addEventListener('click', (event) => addDeletePicID2Set(oneBtn))
    });

    if (gotoCalBtn != null) {
        gotoCalBtn.addEventListener('click', (e) => gotoGoogleCalendar())
    }
    if (deletePSBtn != null) {
        deletePSBtn.addEventListener('click', (e) => pressedDeleteService())
    }
    if (mailtoBtn != null) {
        mailtoBtn.addEventListener('click', (e) => sendMailToCreator(mailtoBtn))
    }

    fetchPopularTypes()
}


function fetchServiceTypes() {
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
    const toBeDltImg = document.querySelector('#carousel-in-modal-' + pictureID)
    console.log(picturesToDelete.includes(pictureID));

    if (picturesToDelete.includes(pictureID)) {
        let id_index = picturesToDelete.indexOf(pictureID)
        picturesToDelete.splice(id_index, 1)
        console.log(pictureID + ' was removed from delete pending set!');
        console.log(picturesToDelete + ' are pending for delete');
        toBeDltImg.classList.remove('pendingForDelete')
    } else {
        picturesToDelete.push(pictureID)
        console.log(pictureID + ' was added to delete pending set!');
        console.log(picturesToDelete + ' are pending for delete');

        toBeDltImg.classList.add('pendingForDelete')

    }
}

function cancelDeletePicSet() {
    picturesToDelete.length = 0
    document.querySelectorAll('.pendingForDelete').forEach(oneNode => { oneNode.classList.remove('pendingForDelete') })
}



function pressedUpdateService() {
    let form = document.getElementById('update-ps-service-form')
    let formDataObj = new FormData(form)
    formDataObj.append('picIDsToDelete', picturesToDelete)

    Swal.fire({
        title: '確認更新資料嗎？',
        text: "刪除的照片沒辦法回復哦！",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '確認更新！'
    }).then((result) => {
        if (result.isConfirmed) {
            ajaxUpdateService(formDataObj)
        }
    })

}


function ajaxUpdateService(form) {
    console.log(form);
    axios({
        method: 'put',
        url: ContextPathname + '/photoService/api/Update',
        data: form
    })
        .then(res => {
            // console.log(res.data);
            location.reload()
        })
        .catch(err => {
            console.log(err);
        })
}


function pressedDeleteService() {
    let form = new FormData()
    const input_serviceID = document.getElementById('serviceID')
    console.log(input_serviceID);

    const serviceID = input_serviceID.value
    console.log(serviceID);
    form.append('serviceID', serviceID)

    Swal.fire({
        title: '你確定要刪除這筆服務嗎？',
        text: "刪除後就沒辦法復原囉！",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '對，確定刪除！'
    }).then((result) => {
        if (result.isConfirmed) {
            axios({
                method: 'delete',
                url: ContextPathname + '/photoService/api/Delete',
                data: form
            })
                .then(res => {
                    Swal.fire(
                        '刪除成功！',
                        '這筆資料已經刪除囉！',
                        'success'
                    )
                    // console.log(res.data);
                    history.back()
                })
                .catch(err => {
                    console.log(err);
                })
        }
    })



}







// Some simple relocate for send mail to service creator & go to my google calendar
function sendMailToCreator(btn) {
    let mail_target = `mailto:${btn.dataset.recipient}?subject=${btn.dataset.subject}`
    console.log(mail_target);
    window.location.href = mail_target
}

function gotoGoogleCalendar() {
    window.location.href = 'https://calendar.google.com/calendar'
}



function fetchPopularTypes() {
    const topTypes = []
    axios({
        method: 'get',
        url: ContextPathname + '/serviceType/api/popularAndCount'
    })
        .then(res => {
            makeTypeLink(res.data)
        })
        .catch(err => {
            console.log(err);
        })
}

function makeTypeLink(serviceTypesAndCount) {
    const popularTypes = document.querySelector('#popularTypes')
    const typeCount = document.querySelector('#typeCount')

    serviceTypesAndCount.forEach(ele => {
        let onelink = document.createElement('li')
        typeLink = `<a href="${ContextPathname}/photoService/ReadByType?serviceType=${ele.serviceType.typeName}">${ele.serviceType.typeName}</a>`
        onelink.innerHTML = typeLink
        popularTypes.append(onelink)

        let oneCount = document.createElement('li')
        oneCount.innerText = `${ele.count}筆`
        typeCount.append(oneCount)

    });
}






// Deprecated function, deleting ReferencePicture is now controlled by updating PhotoService
/*
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
}*/
