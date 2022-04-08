let logoutButton = document.querySelector("#logout");

logoutButton.addEventListener('click', () => {
    localStorage.removeItem('jwt');
    localStorage.removeItem('user_id');
    localStorage.removeItem('username');
    window.location = 'index.html';
});

window.addEventListener('load', (event) => {
    populateReimbursementsTable();
});

async function populateReimbursementsTable() {

    const URL = 'http://localhost:8080/reimbursements';

    let res = await fetch(URL, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwt')}` 
        }
    })

    if (res.status === 200) {
        
        let reimbursements = await res.json();

        let pendingReimbursements = reimbursements.filter(element => element.status === 1);

        let table = document.querySelector('#reimbursements-table');

        let pending = document.querySelector('#pending');
        let all = document.querySelector('#all');

        all.addEventListener('click', () => {
            let tableBody = document.querySelector('#reimbursements-table-body')

            if (tableBody.childElementCount > 0) {
                table.removeChild(tableBody);
            }            
            let newBody = document.createElement('tbody');
            newBody.setAttribute("id", "reimbursements-table-body");

            table.appendChild(newBody);
            getReimbs(reimbursements)  
        });

        pending.addEventListener('click', () => {
            let tableBody = document.querySelector('#reimbursements-table-body')

            if (tableBody.childElementCount > 0) {
                table.removeChild(tableBody);
            }
            let newBody = document.createElement('tbody');
            newBody.setAttribute("id", "reimbursements-table-body");

            table.appendChild(newBody);
            getReimbs(pendingReimbursements)  
        });

        // return reimbursements;
        // possibly not doing anything with the return above?
    }
}

async function patchReimbursement(reimbursement_id, inputStatus) {
    const URL = `http://localhost:8080/reimbursements/${reimbursement_id}?status=${inputStatus}`;

    let res = await fetch(URL, {
        method: 'PATCH',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        },
    })

    if (res.status === 200) {
        let r = await res.json();
        window.alert(`You have successfully updated Reimbursement: ${reimbursement_id}`);
        window.location.reload();
    }
}

function getReimbs(reimbArray) {

    for (let reimbursement of reimbArray) {
        let tr = document.createElement('tr');
        tr.setAttribute("id", "reimbursement-table-row");

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
        receiptImg.setAttribute('src', `http://localhost:8080/reimbursements/${reimbursement.id}/receipt`);
        receiptImg.style.width = "50px";
        td7.appendChild(receiptImg);

        let td8 = document.createElement('td');
        td8.innerText = reimbursement.status;
        td8.innerText = `${reimbursement.status === 2 ? 'Approved' : 'DENIED'}`;
        td8.innerText = `${reimbursement.status === 1 ? 'Pending' : td8.innerText}`;
        if (reimbursement.status === 1) {
            td8.style.cursor = 'pointer'
            let reimbursement_id = reimbursement.id;
            td8.addEventListener('click', () => {
                let givenStatus = window.prompt("Please enter a status [2 for APPROVE/ 3 for DENIAL]", 1);      
                if (givenStatus === "2" || givenStatus === "3") {
                    patchReimbursement(reimbursement_id, givenStatus);
                }
            });
        } 

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



        let tbody = document.querySelector('#reimbursements-table-body')
        tbody.appendChild(tr);                
    }
}

