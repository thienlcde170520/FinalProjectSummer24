document.addEventListener('DOMContentLoaded', () => {
    const passwordInput = document.getElementById('password');
    const confirmPasswordInput = document.getElementById('con_password');
    const passwordMessage = document.getElementById('passwordMessage');
    const confirmPasswordMessage = document.getElementById('conPasswordMessage');

    const validatePassword = (input, messageElement) => {
        const passValue = input.value;
        const passCheck = passValue.length >= 5 && /[a-zA-Z]/.test(passValue) && /\d/.test(passValue);

        if (!passCheck) {
            input.classList.add('invalid');
            input.classList.remove('valid');
            messageElement.textContent = "5 characters or more, at least 1 number and 1 letter.";
        } else {
            input.classList.remove('invalid');
            input.classList.add('valid');
            messageElement.textContent = '';
        }
    };

    passwordInput.addEventListener('input', () => {
        validatePassword(passwordInput, passwordMessage);
    });

    passwordInput.addEventListener('focus', () => {
        if (!passwordInput.classList.contains('valid')) {
            passwordInput.classList.add('invalid');
            passwordMessage.textContent = "5 characters or more, at least 1 number and 1 letter.";
        }
    });

    passwordInput.addEventListener('blur', () => {
        if (passwordInput.value === '') {
            passwordInput.classList.remove('invalid', 'valid');
            passwordMessage.textContent = '';
        }
    });

    confirmPasswordInput.addEventListener('input', () => {
        validatePassword(confirmPasswordInput, confirmPasswordMessage);
    });

    confirmPasswordInput.addEventListener('focus', () => {
        if (!confirmPasswordInput.classList.contains('valid')) {
            confirmPasswordInput.classList.add('invalid');
            confirmPasswordMessage.textContent = "5 characters or more, at least 1 number and 1 letter.";
        }
    });

    confirmPasswordInput.addEventListener('blur', () => {
        if (confirmPasswordInput.value === '') {
            confirmPasswordInput.classList.remove('invalid', 'valid');
            confirmPasswordMessage.textContent = '';
        }
    });
});
