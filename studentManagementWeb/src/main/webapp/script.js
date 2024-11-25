

function generateElement(elementType, classNames, isRequired = false) {
    let newElement = document.createElement(elementType);

    if (classNames) {
        for (let className of classNames) {
            newElement.classList.add(className);
        }
    }

    if (isRequired) {
        newElement.required = true;
    }

    return newElement;
}

function popup(title) {
    let shadow = document.getElementById('shadow');
    let popup = document.getElementById('popup');
    
    let popupHeader = generateElement("div" ,["rowContainer", "greyBG"]);
    let padding = generateElement("div", ["majority", "paddingSmall"]);
    padding.textContent = title;
    
    let outButton = generateElement("div", ["button", "paddingSmallW"]);
    outButton.textContent = "X";
    outButton.addEventListener("click", removePopup);
    
    popupHeader.appendChild(padding);
    popupHeader.appendChild(outButton);
    
    popup.appendChild(popupHeader);
    shadow.classList.remove("disable");
    popup.classList.remove("disable");
    return popup;
}

function removePopup() {
    let shadow = document.getElementById('shadow');
    let popup = document.getElementById('popup');
    
    popup.innerHTML = "";
    shadow.classList.add("disable");
    popup.classList.add("disable");
}

function add(title, list) {
    var pu = popup(title);
    var form = generateElement("form", ["form", "colContainer", "majority"]); // Create a form element

    list.forEach((item, index) => {
        var inputContainer = generateElement("div", ["rowContainer", "paddingSmall"]);
        var label = generateElement("label", ["label", "minority"]);
        label.textContent = item;

        var input = generateElement("input", ["input", "median"], true); 
        input.setAttribute("type", "text"); // Set input type to text
        input.setAttribute("name", item); // Set input name
        input.setAttribute("data-order", index + 1); // Set custom data-order attribute

        inputContainer.appendChild(label); // Append label to input container
        inputContainer.appendChild(input); // Append input to input container
        form.appendChild(inputContainer); // Append input container to form
    });
    
    var serverMSG = generateElement("div", ["redText", "paddingSmall"]);
    
    
//     Event listener for form submission
    form.addEventListener("submit", function(event) {
        event.preventDefault(); // Prevent default form submission
        addEntry(form, serverMSG); // Call function to handle form submission
    });

    pu.appendChild(form); // Append form to the popup
    pu.appendChild(serverMSG);
    var footer = generateElement("div", ["rowContainer", "paddingSmall"]);
    var padding = generateElement("div", ["majority"]);
    
    var sendButton = generateElement("input", ["button", "greyBG", "cell", "paddingSmall"]);
    sendButton.setAttribute("type", "submit");
    sendButton.value = "thêm"; // Use value attribute for input type="submit"

    footer.appendChild(padding);
    footer.appendChild(sendButton);
    form.appendChild(footer);
}


function addEntry(form, sign) {
    console.log("data gathered: \n");

    // Gather form data
    var formData = new FormData(form);
    let encodedFormData = new URLSearchParams(formData).toString();
    
    // Send form data to server using AJAX
    var xhr = new XMLHttpRequest();
    xhr.open("POST", '/studentManagementWeb/add-entry', true); // Replace with your server endpoint
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                
                removePopup();
                document.open(); // Clear the existing document content
                document.write(xhr.responseText); // Write the received content to the document
                document.close(); // Close the document
            } else {
                //print the error text:
                sign.textContent = xhr.responseText;
            }
        }
    };
    console.log("Form data:", formData);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    console.log(encodedFormData);
    xhr.send(encodedFormData);
}    


function edit(title, list, tableData) {
    
    var pu = popup(title);
    var form = generateElement("form", ["form", "colContainer", "majority"]); // Create a form element

    // Create the select dropdown for choosing the row
    var selectContainer = generateElement("div", ["rowContainer", "paddingSmall"]);
    var selectLabel = generateElement("label", ["label", "minority"]);
    selectLabel.textContent = "Select ID:";
    var select = generateElement("select", ["select", "paddingSmall"], true);

    // Populate the options of the select dropdown with keys from the table data
    tableData.forEach((key) => {
        var option = generateElement("option", [], true);
        option.textContent = key[0];
        select.appendChild(option);
    });
    select.value = "null";
    selectContainer.appendChild(selectLabel);
    selectContainer.appendChild(select);
    form.appendChild(selectContainer);

    // Create input fields for each item in the list
    list.forEach((item, index) => {
        var inputContainer = generateElement("div", ["rowContainer", "paddingSmall"]);
        var label = generateElement("label", ["label", "minority"]);
        label.textContent = item;

        var input = generateElement("input", ["input", "median"]);
        input.setAttribute("type", "text");
        input.setAttribute("name", item); // Set input name
        input.setAttribute("data-order", index + 1); // Set custom data-order attribute

        inputContainer.appendChild(label); // Append label to input container
        inputContainer.appendChild(input); // Append input to input container
        form.appendChild(inputContainer); // Append input container to form
    });

    select.addEventListener("change", function(event) {
        // Get the selected key from the combobox
        var selectedKey = select.value;

        // Find the corresponding row in the table data
        var selectedRow;
        for (var i = 0; i < tableData.length; i++) {
            if (tableData[i][0].trim() === selectedKey) {
                selectedRow = i;
                break;
            }
        }

        // Update the input fields based on the selected row
        list.forEach((item, index) => {
            var input = form.querySelector("input[name='" + item + "']");
            if (input) {
                input.value = tableData[selectedRow][index];
            }
        });
    });


    var serverMSG = generateElement("div", ["redText", "paddingSmall"]);

    pu.appendChild(form); // Append form to the popup
    pu.appendChild(serverMSG);

    var footer = generateElement("div", ["rowContainer", "paddingSmall"]);
    var padding = generateElement("div", ["majority"]);

    var saveButton = generateElement("div", ["button", "greyBG", "cell", "paddingSmall"]);
    saveButton.textContent = "Lưu";
    saveButton.addEventListener("click", function() {
        console.log("edit called!");
        saveEdit(select.value, form, serverMSG);
    });
    
    var deleteButton = generateElement("div", ["button", "greyBG", "cell", "paddingSmall"]);
    deleteButton.textContent = "Xóa";
    deleteButton.addEventListener("click", function() {
        
        del(select.value, serverMSG);
    });

    footer.appendChild(padding);
    footer.appendChild(deleteButton);
    footer.appendChild(saveButton);
    form.appendChild(footer);
}

function del(ID ,sign) {
    if (ID === "null" || ID === "") {
        sign.textContent = "Chưa chọn dòng dữ liệu!";
        return;
    }

    // Send form data to server using AJAX
    var xhr = new XMLHttpRequest();
    xhr.open("POST", '/studentManagementWeb/delete-entry?ID=' + ID, true); // Replace with your server endpoint
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                
                removePopup();
                document.open(); // Clear the existing document content
                document.write(xhr.responseText); // Write the received content to the document
                document.close(); // Close the document
            } else {
                //print the error text:
                sign.textContent = xhr.responseText;
            }
        }
    };
    xhr.send();
}



function saveEdit(ID, form, sign) {
    sign.textContent = "hello, this is save edit";
    console.log("hello, this is save edit");
    if (ID === "null" || ID === "") {
        sign.textContent = "Chưa chọn dòng dữ liệu!";
        return;
    }

    // Gather form data
    var formData = new FormData(form);
    formData.append("INIT_ID", ID);
    let encodedFormData = new URLSearchParams(formData).toString();
    // Send form data to server using AJAX
    var xhr = new XMLHttpRequest();
    xhr.open("POST", '/studentManagementWeb/edit-entry', true); // Replace with your server endpoint
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                
                removePopup();
                document.open(); // Clear the existing document content
                document.write(xhr.responseText); // Write the received content to the document
                document.close(); // Close the document
            } else {
                //print the error text:
                sign.textContent = xhr.responseText;
            }
        }
    };
    console.log("Form data:", formData);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    console.log(encodedFormData);
    xhr.send(encodedFormData);
}

function sort(tableData, headerList) {
    let sortable = false;
    let sortIndex = -1;
    for (let i = 0; i < headerList.length; i++) {
        if (headerList[i] === "NAME") {
            sortable = true;
            sortIndex = i;
            break;
        } 
    }
    
    if (sortable === false) {
        return;
    }
    
    let sortedTable = sortArray(tableData, sortIndex);
    
    var table = document.getElementById("tableBody");
    var sortButton = document.getElementById("sortButton");
    table.innerHTML = "";
    
    if (table.classList.contains("unsorted")) {
        
        for (let i = sortedTable.length -1; i >=0; i-- ) {
            let row = generateElement("tr", ["bottomBorder", "borderBox"]);
            table.appendChild(row);
            for (let j = 0; j < sortedTable[0].length; j++) {
                let cell = generateElement("td", ["paddingMed", "cell"]);
                cell.textContent = sortedTable[i][j];
                row.appendChild(cell);
            }
        }
        sortButton.textContent = "Sắp Xếp (A-Z)";
        table.classList.remove(...table.classList);
        table.classList.add("az");
    } else if (table.classList.contains("az")){
        
        for (let i = 0; i < sortedTable.length; i++ ) {
            let row = generateElement("tr", ["bottomBorder", "borderBox"]);
            table.appendChild(row);
            for (let j = 0; j < sortedTable[0].length; j++) {
                let cell = generateElement("td", ["paddingMed", "cell"]);
                cell.textContent = sortedTable[i][j];
                row.appendChild(cell);
            }
        }
        sortButton.textContent = "Sắp Xếp (Z-A)";
        table.classList.remove(...table.classList);
        table.classList.add("za");
    } else {
        
        for (let i = 0; i < tableData.length; i++ ) {
            let row = generateElement("tr", ["bottomBorder", "borderBox"]);
            table.appendChild(row);
            for (let j = 0; j < tableData[0].length; j++) {
                let cell = generateElement("td", ["paddingMed", "cell"]);
                cell.textContent = tableData[i][j];
                row.appendChild(cell);
            }
        }
        sortButton.textContent = "Sắp Xếp (NOR)";
        table.classList.remove(...table.classList);
        table.classList.add("unsorted");
    }
    
}

function sortArray(tableData, sortIndex) {
    let result = [];
    let banIndex = [];
    for (let i = 0; i < tableData.length; i++) {
        let max = "";
        let maxIndex = -1;
        for (let j = 0; j < tableData.length; j++) {
            if (tableData[j][sortIndex] > max) {
                let valid = true;
                for (let z = 0; z < banIndex.length; z++) {
                    if (j === banIndex[z]) {
                        valid  = false;
                        break;
                    }
                }
                if (valid === true) {
                    max = tableData[j][sortIndex]; 
                    maxIndex = j;
                }
            }
        }
        result.push(tableData[maxIndex]);
        banIndex.push(maxIndex);
    } 
    return result;
}

function find() {
    var sb = document.getElementById("searchbar");
    let query = sb.value;
    
    var xhr = new XMLHttpRequest();
    let url = '/studentManagementWeb/dashboard?query=' + query;
    console.log(url);
    xhr.open('GET', url, true);
    xhr.onload = function () {
        if (xhr.status === 200) {
            document.open(); // Clear the existing document content
            document.write(xhr.responseText); // Write the received content to the document
            document.close(); // Close the document
        } else {
            // Request failed, handle error
            console.error('Request failed with status', xhr.status);
        }
    };
    xhr.onerror = function () {
        // Handle network errors
        console.error('Request failed');
    };
    xhr.send();
}

function switchTable(table) {
    var xhr = new XMLHttpRequest();
    let url = '/studentManagementWeb/dashboard?table=' + table;
    console.log(url);
    xhr.open('GET', url, true);
    xhr.onload = function () {
        if (xhr.status === 200) {
            document.open(); // Clear the existing document content
            document.write(xhr.responseText); // Write the received content to the document
            document.close(); // Close the document
        } else {
            // Request failed, handle error
            console.error('Request failed with status', xhr.status);
        }
    };
    xhr.onerror = function () {
        // Handle network errors
        console.error('Request failed');
    };
    xhr.send();
}

function showDetails(i) {
    var pu = popup("Thông tin");
    var row = document.getElementById('row' + i);
//    console.log(row);
    var list = row.getElementsByTagName('td');
//    console.log(list);
    var key = list[0].textContent.trim();
//    console.log(key);
    var xhr = new XMLHttpRequest();
    let url = '/studentManagementWeb/show-more-info?id=' + key;
//    console.log(url);
    xhr.open('GET', url, true);
    xhr.onload = function () {
        if (xhr.status === 200) {
            var selectContainer = generateElement("div", ["rowContainer", "paddingSmall"]);
            var selectLabel = generateElement("label", ["label", "minority"]);
            selectLabel.textContent = "Select year:";
            var select = generateElement("select", ["select", "paddingSmall"], true);

            // ideally this should get the years available in the database, but I can't find a good enough implementation
            let defaultOption = generateElement("option", [], true);
            defaultOption.textContent = 'all';
            select.appendChild(defaultOption);
            for (let i = 2000; i <= 2050; i++) {
                var option = generateElement("option", [], true);
                option.textContent = i;
                select.appendChild(option);
            };
            
            
            select.value = "all";
            selectContainer.appendChild(selectLabel);
            selectContainer.appendChild(select);
            pu.appendChild(selectContainer);
                     
            var table = generateElement("div", ["scrollable"]);
              
            select.addEventListener("change", function(event) {
                filterYear(select.value, table, key);
            });
            
            table.innerHTML = xhr.responseText;
            pu.append(table);
//            document.close(); 
        } else {
            // Request failed, handle error
            console.error('Request failed with status', xhr.status);
        }
    };
    xhr.onerror = function () {
        // Handle network errors
        console.error('Request failed');
    };
    xhr.send();
    
}

function filterYear(year, container, id) {
    var xhr = new XMLHttpRequest();
    let url = '/studentManagementWeb/show-more-info?id=' + id;
    
    if (year != 'all' || year != '') {
        url += "&year=" + year;
    }
    
    xhr.open('GET', url, true);
    xhr.onload = function () {
        if (xhr.status === 200) {
            console.log("send");
            container.innerHTML = xhr.responseText;
        } else {
            // Request failed, handle error
            console.error('Request failed with status', xhr.status);
        }
    }
    
    xhr.onerror = function () {
        // Handle network errors
        console.error('Request failed');
    };
    
    xhr.send();
}

