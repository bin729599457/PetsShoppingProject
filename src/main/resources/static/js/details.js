$(function () {
    var basePetUrl = 'http://leewaiho.com:20002/pets'
    var baseCartUrl = 'http://leewaiho.com:20002/shoppingCart'

    var list = []

    // 上一个页面传过来的宠物的id
    var id = Cookies.get('petId')
    $.post(basePetUrl + '/getSinglePetInfo', {id: id}, function (res) {
        if (res.success) {
            var data = res.obj
            // 图片路径
            $('#detailsContainer .img img').attr('src', data.imgPath)
            // 宠物描述
            $('#detailsContainer .description .title').text(data.petsDesc)
            // 宠物价格
            $('#detailsContainer .description .prize span').text('￥' + data.petsPrice)
            // 立即购买会用到
            list.push(data)
        }
        else {
            alert(res.msg)
        }
    })


    // 头部右上角该显示什么
    if (Cookies.get('id')) {
        $('.personal-center').show()
    } else {
        $('#loginRegister').show()
    }

    // 当鼠标移入个人中心，显示弹窗
    $('.personal-center').mouseover(function () {
        $('#personalDialog').show()
    })
    // 当鼠标离开个人中心的弹窗，隐藏
    $('#personalDialog').mouseout(function () {
        $(this).hide()
    })

    // 点击了头部的logo
    $('#toIndex').click(function () {
        window.location.href = './index.html'
    })
    // 点击了个人中心-我的订单
    $('#toOrder').click(function () {
        window.location.href = './order.html'
    })
    // 点击了个人中心-我的购物车
    $('#toCart').click(function () {
        window.location.href = './cart.html'
    })
    // 点击了个人中心-个人信息管理
    $('#toInformation').click(function () {
        window.location.href = './information.html'
    })
    // 点击了退出登录
    $('#logout').click(function () {
        Cookies.remove('id')
        alert('已退出登录')
        window.location.href = './index.html'
    })

    // 点击了数量中的加号
    $('#detailsContainer').on('click', '.increase', function () {
        var num = Number($('#detailsContainer .data-num').text())
        if (num == 1) {
            $('#detailsContainer .decrease').css('cursor', 'pointer')
        }
        $('#detailsContainer .data-num').text(num + 1)
    })
    // 点击了数量中的减号
    $('#detailsContainer').on('click', '.decrease', function () {
        var num = Number($('#detailsContainer .data-num').text())
        if (num == 2){
            $(this).css('cursor', 'not-allowed')
            $('#detailsContainer .data-num').text(num - 1)
        } else if (num > 2) {
            $('#detailsContainer .data-num').text(num - 1)
        }
    })

    // 点击了加入购物车按钮
    $('#detailsContainer').on('click', '#cart', function () {
        var userId = Cookies.get('id')
        if (userId) {
            var params = {
                userId: Cookies.get('id'),
                petsId: id,
                nums: Number($('#detailsContainer .data-num').text())
            }
            $.post(baseCartUrl + '/addCart', params, function (res) {
                // console.log(res)
                if (res.success) {
                    alert('已加入购物车！')
                } else {
                    alert(res.msg)
                }
            })
        }
        else {
            alert('请先登录！')
            Cookies.set('url', 'details')
            window.location.href = './login.html'
        }
    })
    // 点击了立即购买按钮
    $('#detailsContainer').on('click', '#buy', function () {
        var userId = Cookies.get('id')
        if (userId) {
            // 数量这个页面所选的
            list[0].petsNum = Number($('#detailsContainer .data-num').text())
            Cookies.set('orderList', list)
            window.location.href = './deal.html'
        }
        else {
            alert('请先登录！')
            Cookies.set('url', 'details')
            window.location.href = './login.html'
        }
    })


})
