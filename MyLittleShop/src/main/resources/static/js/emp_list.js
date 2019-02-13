$(function() {
    $("#emp_list").jsGrid({
        height: "auto",
        width: "100%",

        sorting: true,
        paging: false,
        autoload: true,

        controller: {
            loadData: function(filter) {
                return $.ajax({
                    type: "GET",
                    url: "/api/employee/all",
                    data: filter,
                    dataType: "json"
                });
            }
        },

        fields: [
            {name: "account.username", type: "text",title : "Username",align :"left"},
            {name: "account.name", type: "textarea",title :"Employee name",align :"left"},
            {name: "shop.id", type: "number",title :"Shop ID",align :"left"},
            {name: "shop.name", type: "number",title :"Shop Name",align :"left"}
        ]
    });
});

//{"account":{"username":"user1","name":"Vinh","password":"{bcrypt}$2a$10$CQ9DVHMzWRo6ErGkPeplc.CYaMUiHomOx3IhZ8rKkqIi.Zv2CtMyG","active":true,"role":"ROLE_EMPLOYEE"},
// "shop":{"id":1,"name":"android"},"username":"user1"}