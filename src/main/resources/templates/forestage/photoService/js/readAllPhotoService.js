const currentHref = window.location.href
const ContextPathname = currentHref.substring(0,currentHref.indexOf('templates')+9)
console.log(ContextPathname);


const API_url = 'http://localhost:8080/phocos/photoService/api/ReadAll'
const url = ContextPathname+'/photoService/api/ReadAll'

const myBtn = document.querySelector('.my-customer-data-btn');
const output = document.querySelector('.output');

function grabAllPhotoServices() {    
    axios.get(API_url)
        .then(res => {
            console.log(res);
        })
        .catch(err => {
            console.log(err);
        })
}

grabAllPhotoServices();
