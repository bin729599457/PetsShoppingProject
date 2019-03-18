$(function () {
    baseUrl = 'http://localhost:8088/users'

    // 点击了头部的logo
    $('#toIndex').click(function () {
        window.location.href = './index.html'
    })

    // 鼠标点击了选项卡，切换注册、登录
    $('.tab-item').click(function () {
        // 去除所有默认有的active样式，当前点击的加上
        $('.tab-item').removeClass('active')
        $(this).addClass('active')

        // 鼠标点击了注册选项
        if ($(this).text() == '注册') {
            // 清空所有登录表单数据
            $('#accountLogin').val('')
            $('#passwordLogin').val('')
            // 隐藏登录表单
            $('#loginForm').hide()
            // 显示注册表单
            $('#registerForm').show()
        }
        // 鼠标点击了登录选项
        else if ($(this).text() == '登录') {
            // 清空所有注册表单数据
            $('#accountRegister').val('')
            $('#passwordRegister').val('')
            $('#usernameRegister').val('')
            $('#phoneRegister').val('')
            // 隐藏注册表单
            $('#registerForm').hide()
            // 显示登录表单
            $('#loginForm').show()
        }
    })

    // 点击了登录按钮
    $('.btn-login').click(function () {
        // 判断有没输入账号
        if ($('#accountLogin').val()) {
            // 判断有没输入密码
            if ($('#passwordLogin').val()) {
                // 传给后台的参数
                var params = {
                    account: $('#accountLogin').val(),
                    password: $('#passwordLogin').val(),
                }
                $.post(baseUrl + '/login', params, function (res) {
                    if (res.success) {
                        // 存到Cookie去
                        Cookies.set('id', res.obj.id)
                        // 判断是否是从别的页面跳转过来的
                        if (Cookies.get('url')) {
                            // 如果是从详情页跳转过来的
                            if (Cookies.get('url') == 'details') {
                                Cookies.remove('url')
                                window.location.href = './details.html'
                            }
                        } else {
                            window.location.href = './index.html'
                        }
                    } else {
                        alert(res.msg)
                    }
                })
            } else {
                alert('请输入密码')
            }
        } else {
            alert('请输入登录账号')
        }
    })

    // 点击了注册按钮
    $('.btn-register').click(function () {
        // 判断有没输入账号
        if ($('#accountRegister').val()) {
            // 判断有没输入密码
            if ($('#passwordRegister').val()) {
                // 判断有没输入用户昵称
                if ($('#usernameRegister').val()) {
                    if ($('#phoneRegister').val()) {
                        // 传给后台的参数
                        var params = {
                            account: $('#accountRegister').val(),
                            password: $('#passwordRegister').val(),
                            userName: $('#usernameRegister').val(),
                            phone: $('#phoneRegister').val(),
                            isRoot: 0
                        }
                        $.post(baseUrl + '/addUser', params, function (res) {
                            if (res.success) {
                                alert('注册成功！')
                                window.location.href = './login.html'
                            } else {
                                alert(res.msg)
                            }
                        });
                    } else {
                        alert('请输入手机号码')
                    }
                } else {
                    alert('请输入用户昵称')
                }
            } else {
                alert('请输入密码')
            }
        } else {
            alert('请输入登录账号')
        }
    })


    // 点击了找回密码
    $('#findPass').click(function () {
        // 打开重置密码弹窗
        $('.pass-dialog').show()
    })
    // 点击重置密码弹窗的重置密码按钮
    $('.pass-dialog .btn-pass').click(function () {
        // 判断有没输入登录账号
        if ($('#accountPass').val()) {
            // 判断有没输入手机号码
            if ($('#phonePass').val()) {
                // 传给后台的参数
                var params = {
                    account: $('#accountPass').val(),
                    phone: $('#phonePass').val()
                }
                $.post(baseUrl + '/forgetPassword', params, function (res) {
                    // console.log(res)
                    if (res.success) {
                        // 关闭弹窗
                        $('.pass-dialog').hide()
                        // 显示修改成功
                        alert('你原来的密码为：'+ res.obj.password)
                    }
                    else {
                        alert(res.msg)
                    }
                })
            } else {
                alert('请输入手机号码')
            }
        } else {
            alert('请输入登录账号')
        }
    })
    // 点击了重置密码弹窗的关闭
    $('#closePassDialog').click(function () {
        $('.pass-dialog').hide()
    })


})
