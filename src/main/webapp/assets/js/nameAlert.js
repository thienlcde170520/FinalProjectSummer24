document.addEventListener('DOMContentLoaded', () =>{
    const nameInput = document.getElementById('namespace');
    const messName = document.getElementById('messageName');

    nameInput.addEventListener('input', () =>{
        const nameValue = nameInput.value;
        const nameCheck = /^[a-zA-Z0-9]+$/;

        if(!nameCheck.test(nameValue)){
            nameInput.classList.add('invalid');
            nameInput.classList.remove('valid');
            messName.textContent = "Username must not be blank";
        }else{
            nameInput.classList.remove('invalid')
            nameInput.classList.add('valid');
            messName.textContent = '';
        }
    });

    nameInput.addEventListener('focus', () => {
        if (!nameInput.classList.contains('valid')) {
            nameInput.classList.add('invalid');
            messName.textContent = "Username must not be blank";
        }
    });

    nameInput.addEventListener('blur', () => {
        if (nameInput.value === '') {
            nameInput.classList.remove('invalid', 'valid');
            messName.textContent = '';
        }
    });
});