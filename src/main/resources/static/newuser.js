function newuser() {
    // $("#utable").removeClass('active');
    // $("#newusertable").addClass('active');
    fetch("http://localhost:8080/rest/users").then(
        res => {
            res.json().then(
                data => {
                    if (data.length > 0) {
                        var temp = "";

                        data.forEach((user) => {
                            temp += "<tr>";

                            temp += "<td class=\"uid\">" + user.id + "</td>";
                            temp += "<td class=\"uname\">" + user.username + "</td>";
                            temp += "<td class=\"ulast\">" + user.lastName + "</td>";
                            temp += "<td class=\"uage\">" + user.age + "</td>";
                            temp += "<td class=\"uemail\">" + user.email + "</td>";
                            var roles = "";
                            var arrRoles = user.roles;
                            arrRoles.forEach((role) => {
                                var name = role.name;
                                name = name.slice(5)
                                roles += "<span class=\"uroles\">" + name + " </span>";
                            })
                            temp += "<td id=\"uselect\">" + roles + "</td>";
                            temp += '<td><button id="' + user.id + '" type=\"button\" class=\"btn btn-info edit\" data-toggle=\"modal\" data-target=\"editUserModal\" onclick=\"edit(this)\">Edit</button></td>';
                            temp += '<td><button id="del-' + user.id + '" type=\"button\" class=\"btn btn-danger del\" data-toggle=\"modal\" data-target=\"delUserModal\" onclick=\"delshow(this)\">Delete</button></td>';
                            temp += "</tr>";
                            $('#data').html(temp);
                        })
                    }
                }
            )
        }
    )
    // var $editRow = null;
    // $(".edit").click(function (e) {
    //     $editRow = $(this).closest("tr");
    //     $(".userId").val($editRow.find(".uid").text());
    //     $(".userName").val($editRow.find(".uname").text());
    //     $(".userLastName").val($editRow.find(".ulast").text());
    //     $(".userAge").val($editRow.find(".uage").text());
    //     $(".userEmail").val($editRow.find(".uemail").text());
    //     var select = $('#selected');
    //     var roles = [[${allRoles}]];
    //     for (var i = 0; i < roles.length; i++) {
    //         if ($('#select option').length !== roles.length) {
    //             var o = new Option(roles[i].name, roles[i].id);
    //             select.append(o);
    //         }
    //     }
    // });
    // $("#editUserModal").modal('show');
    // $(".del").click(function (e) {
    //     $editRow = $(this).closest("tr");
    //     $(".userId").val($editRow.find(".uid").text());
    //     $(".userName").val($editRow.find(".uname").text());
    //     $(".userLastName").val($editRow.find(".ulast").text());
    //     $(".userAge").val($editRow.find(".uage").text());
    //     $(".userEmail").val($editRow.find(".uemail").text());
    //     var select = $('#select');
    //     var roles = [[${allRoles}]];
    //     for (var i = 0; i < roles.length; i++) {
    //         if ($('#select option').length !== roles.length) {
    //             var o = new Option(roles[i].name, roles[i].id);
    //             select.append(o);
    //         }
    //     }
    //     $("#delUserModal").modal('show');
    // });
    // });
    newuser();
}
