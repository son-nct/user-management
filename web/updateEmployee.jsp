<%-- 
    Document   : createAccount
    Created on : Jun 5, 2021, 7:45:31 PM
    Author     : WIN 10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Employee Page</title>
        <!-- Font Awesome -->
        <link
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css"
            rel="stylesheet"
            />
        <!-- Google Fonts -->
        <link
            href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
            rel="stylesheet"
            />
        <!-- MDB -->
        <link
            href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/3.5.0/mdb.min.css"
            rel="stylesheet"
            />
        <!-- MDB -->
        <script
            type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/3.5.0/mdb.min.js"
        ></script>
        <script type="text/javascript">
            function disableBack() {
                window.history.forward();
            }
            setTimeout("disableBack()", 0);
            window.onunload = () => {
                null;
            };
        </script>
    </head>
    <body>
       <c:set var="user" value="${sessionScope.USER}" scope="session"/>
        <c:if test="${empty user}">
            <c:redirect url=""/>
        </c:if>

        <c:if test="${not empty user and user.role eq 'Admin'}">
            <c:redirect url="manage.jsp"/>
        </c:if>
        <div class="container">
            <header class="d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom">
                <a style="margin-top: -12px" href="info.jsp" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
                    <i style="font-size: 18px;margin-right: 5px" class="far fa-user"></i>
                    <span  class="fs-4">User Management</span>
                </a>


                <div class="dropdown text-end" style="display: flex; margin-right: 15px; padding-top: 2px">
                    <a href="#" class="d-block link-dark text-decoration-none">
                        <img style="object-fit: cover;" src="${user.photo}" alt="mdo" width="32" height="32" class="rounded-circle">
                    </a>
                    <span style="margin: 5px">
                        <p>${user.fullname}</p>
                    </span>

                </div>
                <ul class="nav  justify-content-center">
                    <li style="margin-right: 10px"><a href="info.jsp" class="btn btn-info">Home</a></li>
                    <li><a href="logout"  class="btn btn-info">Logout</a></li>
                </ul>
            </header>
        </div>

        <div class="body-page">
            <div class="container col-xl-10 col-xxl-8 px-0 py-1" >
                <div class="row align-items-center g-lg-5 py-2">
                    <div class="col-md-10 mx-auto col-lg-5" >
                        <form action="updateEmployee" method="POST" class="p-4 p-md-5 border rounded-3 bg-light" style="width: 400px">

                            <h3 style=" text-align: center; margin: -25px 0 20px 0">Update Information</h3>

                            <c:set var="dto" value="${sessionScope.USER_UPDATE}" scope="session"/>
                            <div class="form-floating mb-3">
                                <input value="${dto.username}" type="text" class="form-control" placeholder="Username" disabled="true">
                                <input name="txtUsername"  value="${dto.username}" type="hidden">
                                <label for="floatingInput">Username</label>
                            </div>


                            <div class="form-floating mb-3">
                                <input name="txtEmail" value="${dto.email}" type="text" class="form-control"   placeholder="name@example.com">
                                <label for="floatingInput">Email</label>

                                <c:set var="empty_email" value="${sessionScope.empty_email}" scope="session"/>
                                <c:set var="invalid_email" value="${sessionScope.invalid_email}" scope="session"/>

                                <c:choose>
                                    <c:when test="${not empty empty_email}">
                                        <p style=" color: red">${empty_email}</p>
                                        <c:remove var="empty_email"/>
                                    </c:when>

                                    <c:when test="${not empty invalid_email}">
                                        <p style=" color: red">${invalid_email}</p>
                                        <c:remove var="invalid_email"/>
                                    </c:when>
                                </c:choose>
                            </div>

                            <div class="form-floating mb-3">
                                <input name="txtPhone" value="${dto.phone}" type="text" class="form-control"   placeholder="phone">
                                <label for="floatingInput">Phone</label>

                                <c:set var="empty_phone" value="${sessionScope.empty_phone}" scope="session"/>
                                <c:set var="error_phone" value="${sessionScope.error_phone}" scope="session"/>

                                <c:choose>
                                    <c:when test="${not empty empty_phone}">
                                        <p style=" color: red">${empty_phone}</p>
                                        <c:remove var="empty_phone"/>
                                    </c:when>

                                    <c:when test="${not empty error_phone}">
                                        <p style=" color: red">${error_phone}</p>
                                        <c:remove var="error_phone"/>
                                    </c:when>
                                </c:choose>
                            </div>

                            <div class="form-floating mb-3">
                                <input name="txtFullname" value="${dto.fullname}" type="text" class="form-control"  placeholder="Fullname">
                                <label for="floatingInput">Fullname</label>

                                <c:set var="empty_fullname" value="${sessionScope.empty_fullname}" scope="session"/>

                                <c:if test="${not empty empty_fullname}">
                                    <p style=" color: red">${empty_fullname}</p>
                                </c:if>
                            </div>

                            <div id="overplay" class="position-fixed  start-0 end-0 bottom-0" style="top: 200%;background-color: rgba(255,255,255,0.5)"></div>
                            <div class="d-flex justify-content-center align-items-center position-fixed  start-0 end-0 bottom-0" id="loading" style="top:200%">
                                <div class="spinner-border text-primary" role="status">
                                    <span class="visually-hidden">Loading...</span>
                                </div>
                            </div>

                            <span class="row-input">
                                <label  class="labelLogin">Photo: </label> 
                                <input onchange="uploadImg()" id="file" type="file" name="txtPhoto" value="" placeholder="Please choose Image"/><br/>
                                <div class="img-container" style="text-align: center;">
                                    <img id="img" src="${dto.photo}" style="width: 55%; margin: 15px 0 20px -45%; object-fit: cover;"/>
                                    <input id="urlHidden" type="hidden" value="${dto.photo}" name="urlImg"/>
                                </div>
                            </span>

                            <input id="urlHidden" type="hidden" value="${dto.role}" name="txtRole"/>

                            <c:set var="empty_img" value="${sessionScope.empty_img}" scope="session"/>
                            <c:if test="${not empty empty_img}">
                                <p style=" color: red">${empty_img}</p>
                                <c:remove var="empty_img"/>
                            </c:if>

                            <!-- Buttons trigger collapse -->
                            <div class="form-floating mb-3" style="position: relative">
                                <input name="curPassword" value="" type="password" class="form-control"  placeholder="Password">
                                <label for="floatingPassword">Your Current Password</label>

                                <c:set var="wrong_curPass" value="${sessionScope.ERROR_PASSWORD}" scope="session"/>
                                <c:set var="empty_curPass" value="${sessionScope.EMPTY_CUR_PASWORD}" scope="session"/>

                                <c:if test="${not empty wrong_curPass}">
                                    <p style=" color: red">${wrong_curPass}</p>
                                </c:if>

                                <c:if test="${not empty empty_curPass}">
                                    <p style=" color: red">${empty_curPass}</p>
                                </c:if>
                            </div>

                            <a
                                class="btn btn-primary"
                                data-mdb-toggle="collapse"
                                href="#collapseExample"
                                role="button"
                                aria-expanded="false"
                                aria-controls="collapseExample"
                                >
                                Update Password
                            </a>


                            <!-- Collapsed content -->
                            <div class="collapse mt-3" id="collapseExample">


                                <c:set var="new_password" value="" scope="session"/>
                                <div class="form-floating mb-3" style="position: relative">
                                    <input name="txtPassword" value="${new_password}" type="password" class="form-control"  placeholder="Password">
                                    <label for="floatingPassword">New Password</label>

                                    <c:set var="empty_password" value="${sessionScope.empty_password}" scope="session"/>

                                    <c:if test="${not empty empty_password}">
                                        <p style=" color: red">${empty_password}</p>
                                    </c:if>

                                </div>

                                <div class="form-floating mb-3" style="position: relative">
                                    <input name="txtRepassword" value="" type="password" class="form-control"  placeholder="Repassword">
                                    <label for="floatingPassword">Re-password</label>

                                    <c:set var="not_match" value="${sessionScope.not_match}" scope="session"/>
                                    <c:if test="${not empty not_match}">
                                        <p style=" color: red">${not_match}</p>
                                    </c:if>
                                </div>
                            </div>
                            <button style="width: 100%; margin-top: 15px; font-size: 16px;" type="submit" class="btn btn-success">Update</button>

                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script src="./assets/js/uploadImg.js"></script>
        <script>
            <c:if test="${not empty empty_password or not empty not_match}">
                                    document.getElementById('collapseExample').classList.add("show");
            </c:if>
        </script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </body>
</html>
