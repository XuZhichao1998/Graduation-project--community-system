//消息内容，可以任意长度
let arr = ["消息1 : 变电站进行改造，预计停电3小时，请业主朋友提前做好准备", "消息2 : 近期发现周边小区有人假冒燃气集团工作人员上门向住户推销产品，服务中心提醒广大业主/住户注意", "消息3 :  门禁及安防系统改造，施工期间给居民带来的不便，请大家谅解"];

var settings = {
  speeds: 10, //滚动的速度,单位ms
  isPause: true, //滚动后每个消息是否需要暂停，true和false,
  isHover:true, //鼠标悬停是否需要暂停
};

var ul = document.querySelector("ul");
//渲染数据
arr.forEach((item) => {
  var li = document.createElement("li");
  li.innerHTML = item;
  ul.appendChild(li);
});
//获取当前滚动的高度，支持ie请自行使用currentStyle
var currentTop = parseInt(window.getComputedStyle(ul).top);

//滚动函数
function run() {
  currentTop--;
  ul.style.top = currentTop + "px";
  
  //当页面滚动最后一个时，把第一个复制push到尾部
  if (currentTop == (arr.length - 1) * -50) {
    let li = document.createElement("li");
    li.innerHTML = arr[0];
    ul.appendChild(li);
  }

  //无缝替换
  if (currentTop == arr.length * -50) {
    currentTop = 0;
    ul.style.top = currentTop + "px";
    var li = document.querySelectorAll("li");
    ul.removeChild(li[li.length - 1]);
  }

  //滚动后每个消息是否需要暂停
  if (settings.isPause) {
    if (currentTop % 50 == 0) {
      clearInterval(timer);
      setTimeout(function () {
        timer = setInterval(run, settings.speeds);
      }, 3000);
    }
  }
}
var timer = setInterval(run, settings.speeds);

//鼠标悬停是否需要暂停
ul.onmouseover = function () {
    if(settings.isHover){
        clearInterval(timer);
    }
};
ul.onmouseleave = function () {
    if(settings.isHover){
        timer = setInterval(run, settings.speeds);
    }
};
