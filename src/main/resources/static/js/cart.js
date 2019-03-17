$(function () {
    var baseUrl = 'http://localhost:8080/shoppingCart'

    // 先判断有没登录
    var id = Cookies.get('id')
    if (id) {
        // 获取购物车表格数据
        getTable()
    }
    else {
        alert('请先登录')
        window.location.href = './login.html'
    }

    var cartList = []

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

    // 点击了表格的数量的减号按钮
    $('tbody').on('click', '.decrease', function () {
        var $num = $(this).next()
        // 数量大于1才能减
        if (Number($num.text()) > 1) {
            // 先禁止重复按
            $(this).addClass('disable')
            // 点加号时候的数量再加上1
            var num = Number($num.text()) - 1
            // 找到当前宠物的id
            var petId = $num.attr('data-id')
            // 调用后台接口
            updateNum(petId, num)
        }
    })
    // 点击了表格的数量的加号按钮
    $('tbody').on('click', '.increase', function () {
        // 先禁止重复按
        $(this).addClass('disable')

        var $num = $(this).prev()
        // 点加号时候的数量再加上1
        var num = Number($num.text()) + 1
        // 找到当前宠物的id
        var petId = $num.attr('data-id')
        // 调用后台接口
        updateNum(petId, num)
    })
    // 点击了表格的删除按钮
    $('tbody').on('click', '.cart-item-btn', function () {
        // 先禁用了
        $(this).addClass('disable')
        // 获取这个宠物的id
        var petId = $(this).attr('data-id')
        $.ajax({
            type: 'DELETE',
            url: baseUrl + '/delPetFromCart',
            data: {
                userId: id,
                petsId: petId
            },
            success: function(res){
                if (res.success) {
                    // 重新获取购物车表格数据
                    getTable()
                } else {
                    alert(res.msg)
                }
            }
        })
        // 重新渲染表格
        // getTable()
    })

    // 点击了结算按钮
    $('#buy').click(function () {
        Cookies.set('orderList', cartList)
        window.location.href = './deal.html'
    })

    // 获取购物车表格数据
    function getTable() {
        // 获取购物车数据
        $.post(baseUrl + '/getCartMsgById', { userId: id }, function (res) {
            if (res.success) {
                var list = res.obj
                cartList = list
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
                    '                    <th class="cart-item-actions"></th>\n' +
                    '                </tr>'
                $table.append(th)
                if (list.length == 0) {
                    $('.content .width1200').append('<div style="text-align: center">购物车里空空的~ 快去挑选你喜欢的商品吧！</div>')
                    $('.footer .total span').text('¥' + Number(0).toFixed(2))
                    return
                }
                var total = 0
                $('.footer .total span').text('¥' + total.toFixed(2))
                // 循环表格内容
                for (var i = 0; i < list.length; i++) {
                    var decrease = list[i].petsNum == 1 ? ' disable' : ''
                    var str = '<tr class="item-row">\n' +
                        '                    <td class="cart-item-info">' + list[i].petsDesc + '</td>\n' +
                        '                    <td class="cart-item-price">' + Number(list[i].petsPrice).toFixed(2) + '</td>\n' +
                        '                    <td class="cart-item-picker">\n' +
                        '                        <span class="btn-picker decrease' + decrease + '">-</span><span class="btn-num" data-id=' + list[i].id + '>' + list[i].petsNum + '</span><span class="btn-picker increase">+</span>\n' +
                        '                    </td>\n' +
                        '                    <td class="cart-item-amount">' + Number(list[i].petsNum * list[i].petsPrice).toFixed(2) + '</td>\n' +
                        '                    <td class="cart-item-actions">\n' +
                        '                        <button class="cart-item-btn"  data-id=' + list[i].id + '>删除</button>\n' +
                        '                    </td>\n' +
                        '                </tr>'
                    $table.append(str)
                    total +=  Number(list[i].petsNum * list[i].petsPrice)
                }
                // 更新合计
                $('.footer .total span').text('¥' + total.toFixed(2))
            } else {
                alert(res.msg)
            }
        })
    }
    // 修改购物车某个宠物的数量
    function updateNum(petId, num) {
        // 传给后台的参数
        $.ajax({
            type: 'PUT',
            url: baseUrl + '/updateCartNums',
            data: {
                userId: id,
                petsId: petId,
                nums: num
            },
            success: function(res){
                if (res.success) {
                    // 重新获取购物车表格数据
                    getTable()
                } else {
                    alert(res.msg)
                }
            }
        })
    }
})
