$(function () {
    var baseUrl = 'http://localhost:8088/pets'
    // 分页数据
    var pages = 1
    var rows = 15
    var total = 0
    // 获取宠物列表数据
    getPetData()

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

    // 点击了图片进入详情页
    $('.list').on('click', '.item img', function () {
        Cookies.set('petId', $(this).attr('data-id'))
        window.location.href = './details.html'
    })

    // 点击了加载更多
    $('.loading').click(function () {
        if (!$(this).attr('data-all')) {
            pages += 1
            getPetData()
        }
    })

    // 获取宠物列表数据
    function getPetData() {
        var params = {
            pages: pages,
            rows: rows
        }
        $.post(baseUrl + '/getAllPets', params, function (res) {
            if (res.success) {
                var data = res.obj
                for (var i = 0; i < data.length; i++) {
                    var str = ' <div class="item">\n' +
                        '                <img src=' + data[i].imgPath + ' data-id=' + data[i].id + ' class="img"/>\n' +
                        '                <div class="title" data-id=' + data[i].id + '>' + data[i].petsDesc + '</div>\n' +
                        '                <div class="price">¥' + data[i].petsPrice + '</div>\n' +
                        '            </div>'
                    $('#list').append(str)
                }
                // 总数
                total = res.total
                // console.log(rows * pages)
                // 分页
                if (rows * pages >= total) {
                    $('.loading').text('没有更多了')
                    $('.loading').attr('data-all', true)
                }
            }
            // console.log(res.obj)
        })
    }
})
