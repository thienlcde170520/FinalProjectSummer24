document.addEventListener('DOMContentLoaded', () => {
    const emailInput = document.getElementById('emailInput');
    const message = document.getElementById('message');

    emailInput.addEventListener('input', () => {
        const emailValue = emailInput.value;
        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (!emailPattern.test(emailValue)) {
            emailInput.classList.add('invalid');
            emailInput.classList.remove('valid');
            message.textContent = "Follow form youremail@gmail.com";
        } else {
            emailInput.classList.remove('invalid');
            emailInput.classList.add('valid');
            message.textContent = '';
        }
    });

    emailInput.addEventListener('focus', () => {
        if (!emailInput.classList.contains('valid')) {
            emailInput.classList.add('invalid');
            message.textContent = "Follow form youremail@gmail.com";
        }
    });

    emailInput.addEventListener('blur', () => {
        if (emailInput.value === '') {
            emailInput.classList.remove('invalid', 'valid');
            message.textContent = '';
        }
    });
});
