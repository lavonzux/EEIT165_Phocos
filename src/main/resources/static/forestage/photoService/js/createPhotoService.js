const currentHref = window.location.href
const ContextPathname = currentHref.substring(0, currentHref.indexOf('phocos') + 6)





window.onload = function () {
    fetchServiceTypes()


}
const serviceTypeOptions = document.getElementById('serviceTypeOptions')
console.log(serviceTypeOptions);
serviceTypeOptions.onclick = removeDefaultSelect


function fetchServiceTypes() {
    const serviceTypeOptions = document.getElementById('serviceTypeOptions')

    axios.get(ContextPathname + '/serviceType/api/ReadAll')
        .then(res => {
            const serviceTypesList = res.data
            serviceTypesList.forEach((oneType, index) => {
                const option = document.createElement('option')
                option.setAttribute('value', oneType.typeName)
                option.innerHTML = oneType.typeName
                serviceTypeOptions.appendChild(option)
                document.getElementById('serviceName').focus()
            });
        })
        .catch(err => {
            console.log(err);
        })
}

function removeDefaultSelect() {
    console.log('typeOption pressed');
    const defaultOption = document.getElementById('defaultOption')
    defaultOption.remove();
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



function sendUpdateService(form) {
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
