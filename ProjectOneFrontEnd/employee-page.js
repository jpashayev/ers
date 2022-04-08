let logoutButton = document.querySelector("#logout");

logoutButton.addEventListener('click', () => {
    localStorage.removeItem('jwt');
    localStorage.removeItem('user_id');
    localStorage.removeItem('username');
    window.location = 'index.html';
});

let username = localStorage.getItem('username');
document.querySelector('#welcome-user').innerText = "Welcome " + username + "!";

window.addEventListener('load', (event) => {
    populateReimbursementsTable();
});

let addReimbursement = document.querySelector("#addRem");
addReimbursement.addEventListener("click", async () => {
    let amountInput = document.querySelector("#add-amount");
    let typeInput = document.querySelector("#add-type");
    let fileInput = document.querySelector("#add-file");

    let formData = new FormData();
    formData.append('amount', amountInput.value);
    formData.append('type', typeInput.value);
    formData.append('receipt', fileInput.files[0]);

    try {
        let res = await fetch(`http://localhost:8080/users/${localStorage.getItem('user_id')}/reimbursements`, {
        method: 'POST',
        body: formData,
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
            }
        });

        window.alert("You have successfully added a new Reimbursement!");
        window.location.reload();
        populateReimbursementsTable();
    }   catch (e) {
        window.alert("Reimbursement invalid");
        console.log(e);
    }
});

async function populateReimbursementsTable() {

    const URL = `http://localhost:8080/users/${localStorage.getItem('user_id')}/reimbursements`;

    let res = await fetch(URL, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwt')}` 
        }
    })

    if (res.status === 200) {
        let reimbursements = await res.json();

        let pendingReimbursements = reimbursements.filter(element => element.status === 1);
        let fulfilledReimbursements = reimbursements.filter(element => element.status !== 1);

        let table = document.querySelector('#employee-table');

        let all = document.querySelector('#allRems');
        let previous = document.querySelector('#previous');
        let pending = document.querySelector('#pendingRems');

        all.addEventListener('click', () => {
            let tableBody = document.querySelector('#employee-table-body')

            if (tableBody.childElementCount > 0) {
                table.removeChild(tableBody);
            }            
            let newBody = document.createElement('tbody');
            newBody.setAttribute("id", "employee-table-body");
            table.appendChild(newBody);

            getReimbs(reimbursements)  
        });

        previous.addEventListener('click', () => {
            let tableBody = document.querySelector('#employee-table-body')

            if (tableBody.childElementCount > 0) {
                table.removeChild(tableBody);
            }
            let newBody = document.createElement('tbody');
            newBody.setAttribute("id", "employee-table-body");

            table.appendChild(newBody);
            getReimbs(fulfilledReimbursements)  
        });

        
        pending.addEventListener('click', () => {
            let tableBody = document.querySelector('#employee-table-body')

            if (tableBody.childElementCount > 0) {
                table.removeChild(tableBody);
            }
            let newBody = document.createElement('tbody');
            newBody.setAttribute("id", "employee-table-body");

            table.appendChild(newBody);
            getReimbs(pendingReimbursements)  
        });

        function getReimbs(reimbArray) {

            for (let reimbursement of reimbArray) {
                let tr = document.createElement('tr');

                let td1 = document.createElement('td');
                td1.innerText = reimbursement.id;

                let td2 = document.createElement('td');
                td2.innerText = reimbursement.username;

                let td3 = document.createElement('td');
                td3.innerText = "$" + reimbursement.amount;
                
                
                let td4 = document.createElement('td');
                td4.innerText = reimbursement.type;
                td4.innerText = `${reimbursement.type === 1 ? 'LODGING' : td4.innerText}`;
                td4.innerText = `${reimbursement.type === 2 ? 'TRAVEL' : td4.innerText}`;
                td4.innerText = `${reimbursement.type === 3 ? 'FOOD' : td4.innerText}`;
                td4.innerText = `${reimbursement.type === 4 ? 'OTHER' : td4.innerText}`;
                
                let td5 = document.createElement('td');
                let submissionDate = new Date(reimbursement.submissionDate);
                td5.innerText = `${submissionDate.getDate()}/${submissionDate.getMonth()}/${submissionDate.getFullYear()}\n${Math.floor(submissionDate.getHours()/2)}:${submissionDate.getMinutes()} ${(submissionDate.getHours() > 11 && submissionDate.getHours() < 24) ? 'PM' : 'AM'}`;
                td5.style.visibility = (submissionDate.getFullYear() == 1969 ? 'hidden' : 'visible');
                
                let td6 = document.createElement('td');
                let resolutionDate = new Date(reimbursement.resolutionDate);
                td6.innerText = `${resolutionDate.getDate()}/${resolutionDate.getMonth()}/${resolutionDate.getFullYear()}\n${Math.floor(resolutionDate.getHours()/2)}:${resolutionDate.getMinutes()} ${(resolutionDate.getHours() > 11 && resolutionDate.getHours() < 24) ? 'PM' : 'AM'}`;
                td6.style.visibility = (resolutionDate.getFullYear() == 1969 ? 'hidden' : 'visible');
                
                let td7 = document.createElement('td');
                let receiptImg = document.createElement('img');
                receiptImg.setAttribute('src', `http://localhost:8080/reimbursements/${reimbursement.id}/receipt`)
                receiptImg.style.width = "50px";
                td7.appendChild(receiptImg);

                let td8 = document.createElement('td');
                td8.innerText = reimbursement.status;
                td8.innerText = `${reimbursement.status === 2 ? 'Approved' : 'DENIED'}`;
                td8.innerText = `${reimbursement.status === 1 ? 'Pending' : td8.innerText}`;

                let td9 = document.createElement('td');
                td9.innerText = reimbursement.resolver;

                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tr.appendChild(td5);
                tr.appendChild(td6);
                tr.appendChild(td7);
                tr.appendChild(td8);
                tr.appendChild(td9);

                let tbody = document.querySelector('#employee-table-body')
                tbody.appendChild(tr);
            }
        }
    }
}
