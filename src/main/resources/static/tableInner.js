function show() {
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
}

// $(document).ready(function () {
    show();
    // setInterval('show()', 1000);
// });