let loginButton = document.querySelector('#login-btn');

loginButton.addEventListener('click', async() => {
    let usernameInput = document.querySelector('#username');
    let passwordInput = document.querySelector('#password');

    const URL = 'http://localhost:8080/login';

    const jsonString = JSON.stringify({
        "username": usernameInput.value,
        "password": passwordInput.value
    });

    let res = await fetch(URL, {
        method: 'POST',
        body: jsonString,
        // credentials are important if you are using http session
    });

    let token = res.headers.get('Token');
    localStorage.setItem('jwt', token);


    if (res.status === 200) {
        let user = await res.json();

        localStorage.setItem('user_id', user.id);
        localStorage.setItem('username', user.username);

        if (user.userRole === 'Manager') {
            window.location ='/manager-page.html';
        } 
        else if (user.userRole === 'Employee') {
            window.location ='/employee-page.html';
        }
    }
        else {
            let error = await res.text();
            console.log(error);

            let errorMessage = document.querySelector('#login-error-msg');
            errorMessage.innerText = error.toUpperCase();
            errorMessage.style.visibility = 'visible';

            errorMessage.addEventListener('click', (event) => {
                errorMessage.innerText = '';
            });
        }
});