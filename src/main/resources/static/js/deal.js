$(function () {
    var baseUserUrl = 'http://localhost:8088/users'
    var baseOrderUrl = 'http://localhost:8088/order'

    // 订单信息
    var list = []
    var total = 0

    // 先判断有没登录
    var id = Cookies.get('id')
    if (id) {
        // 获取用户信息
        $.post(baseUserUrl + '/getSingleUsersInfo', { id: id }, function (res) {
            if (res.success) {
                var data = res.obj
                // 渲染出来
                $('#username').val(data.userName)
                $('#phone').val(data.phone)
                $('#address').val(data.address)
            } else {
                alert(res.msg)
            }
        })
        // 获取订单信息
        list = JSON.parse(Cookies.get('orderList'))
        // 渲染出来
        // 获取到表格的jq对象
        var $table = $('.cart-table tbody')
        // 清空表格
        $table.empty()
        // 先加入表头
        var th = '<tr class="th">\n' +
            '                    <th class="cart-item-info">商品信息</th>\n' +
            '                    <th class="cart-item-price">单价（元）</th>\n' +
            '                    <th class="cart-item-picker">数量</th>\n' +
            '                    <th class="cart-item-amount">小计（元）</th>\n' +
            '                </tr>'
        $table.append(th)
        total = 0
        // 循环表格内容
        for (var i = 0; i < list.length; i++) {
            var decrease = list[i].petsNum == 1 ? ' disable' : ''
            var str = '<tr class="item-row">\n' +
                '                    <td class="cart-item-info">' + list[i].petsDesc + '</td>\n' +
                '                    <td class="cart-item-price">' + Number(list[i].petsPrice).toFixed(2) + '</td>\n' +
                '                    <td class="cart-item-picker">' + list[i].petsNum + '</td>\n' +
                '                    <td class="cart-item-amount">' + Number(list[i].petsNum * list[i].petsPrice).toFixed(2) + '</td>\n' +
                '                </tr>'
            $table.append(str)
            total +=  Number(list[i].petsNum * list[i].petsPrice)
        }
        // 合计
        $('.footer .total span').text('¥' + total.toFixed(2))
    }
    else {
        alert('请先登录')
        window.location.href = './login.html'
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

    // 点击了确认下单按钮
    $('#buy').click(function () {
        var params = {
            customerId: id,
            sumPrice: total,
            address: $('#address').val(),
            phone: $('#phone').val(),
            receiverName: $('#username').val(),
            cartList: []
        }
        // 过滤参数
        var cartList = []
        for(var i = 0; i < list.length; i++) {
            var obj = {
                petsId: '',
                petsNums: 0
            }
            obj.petsId = list[i].id
            obj.petsNums = list[i].petsNum
            cartList.push(obj)
        }
        params.cartList = cartList
        // console.log(params)
        $.ajax({
            type: 'POST',
            url: baseOrderUrl + '/makeOrder',
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(params),
            success: function(res){
                console.log(res)
                if (res.success) {
                    alert('下单成功！')
                    window.location.href = './order.html'
                } else {
                    alert(res.msg)
                }
            }
        })
    })

})
