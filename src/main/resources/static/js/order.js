$(function () {
    var baseUrl = 'http://leewaiho.com:20002/order'

    // 先判断有没登录
    var id = Cookies.get('id')
    if (id) {
        getTable()
    } else {
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
    // 点击了个人中心-我的购物车
    $('#toCart').click(function () {
        window.location.href = './cart.html'
    })
    // 点击了个人中心-个人信息管理
    $('#toInformation').click(function () {
        window.location.href = './information.html'
    })

    // 点击了立即支付按钮
    $('.cart-table').on('click', '#pay', function () {
        $.ajax({
            type: 'PUT',
            url: baseUrl + '/updateOrders',
            data: {
                orderId: $(this).attr('data-id'),
                status: 2
            },
            success: function(res){
                if (res.success) {
                    alert('支付成功！')
                    getTable()
                } else {
                    alert(res.msg)
                }
            }
        })
    })

    // 获取订单表格数据
    function getTable() {
        $.post(baseUrl + '/getAllOrders', {customerId: id, pages: 1, rows: 50}, function (res) {
            console.log(res)
            if (res.success) {
                var list = res.obj
                // 渲染出来
                // 获取到表格的jq对象
                var $table = $('.cart-table tbody')
                // 清空表格
                $table.empty()
                // 先加入表头
                var th = '<tr class="th">\n' +
                    '                <th>订单号</th>\n' +
                    '                <th>收货人姓名</th>\n' +
                    '                <th>收货人手机</th>\n' +
                    '                <th>收货地址</th>\n' +
                    '                <th class="cart-item-amount">总价（元）</th>\n' +
                    '                <th>状态</th>\n' +
                    '                <th>操作</th>\n' +
                    '            </tr>'
                $table.append(th)
                // 循环表格内容
                for (var i = 0; i < list.length; i++) {
                    var status = ''
                    var btn = ''
                    if (list[i].status == 1) {
                        status = '待支付'
                        btn = '<button id="pay" data-id=' + list[i].id + '>立即支付</button>'
                    }
                    else if (list[i].status == 2) {
                        status = '等待配送'
                        btn = '无'
                    }
                    else if (list[i].status == 3) {
                        status = '已完成'
                        btn = '无'
                    }

                    var str = '<tr class="item-row">\n' +
                        '                    <td>' + list[i].id + '</td>\n' +
                        '                    <td>' + list[i].receiverName + '</td>\n' +
                        '                    <td>' + list[i].phone + '</td>\n' +
                        '                    <td>' + list[i].address + '</td>\n' +
                        '                    <td class="cart-item-amount">' + Number(list[i].sumPrice).toFixed(2) + '</td>\n' +
                        '                    <td>' + status + '</td>\n' +
                        '                    <td>' + btn + '</td>\n' +
                        '                </tr>'
                    $table.append(str)
                }
            } else {
                alert(res.msg)
            }
        })
    }


})