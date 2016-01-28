angular.module('myApp',[],function($httpProvider){
	//angular的一些定制
	//改变$http的行为模式，使其与jq一致
	 // Use x-www-form-urlencoded Content-Type
	  $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
	 
	  /**
	   * The workhorse; converts an object to x-www-form-urlencoded serialization.
	   * @param {Object} obj
	   * @return {String}
	   */ 
	  var param = function(obj) {
	    var query = '', name, value, fullSubName, subName, subValue, innerObj, i;
	      
	    for(name in obj) {
	      value = obj[name];
	        
	      if(value instanceof Array) {
	        for(i=0; i<value.length; ++i) {
	          subValue = value[i];
	          fullSubName = name + '[' + i + ']';
	          innerObj = {};
	          innerObj[fullSubName] = subValue;
	          query += param(innerObj) + '&';
	        }
	      }
	      else if(value instanceof Object) {
	        for(subName in value) {
	          subValue = value[subName];
	          fullSubName = name + '[' + subName + ']';
	          innerObj = {};
	          innerObj[fullSubName] = subValue;
	          query += param(innerObj) + '&';
	        }
	      }
	      else if(value !== undefined && value !== null)
	        query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
	    }
	      
	    return query.length ? query.substr(0, query.length - 1) : query;
	  };
	 
	  // Override $http service's default transformRequest
	  $httpProvider.defaults.transformRequest = [function(data) {
	    return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
	  }];
	
}).controller('userCtrl',function($scope,$http){
	//dialog
	$scope.dialog={
			show:false,
			title:'警告',
			content:'',
			type:'alert alert-warning',
			success:function(title,content){
				this.show=true;
				this.type='alert alert-success';
				this.title=title;
				this.content=content;
			},
			warning:function(title,content){
				this.show=true;
				this.type='alert alert-warning';
				this.title=title;
				this.content=content;
			},
			hide:function(){
				this.show=false;
			}
	};
	//业务逻辑比较相似，我做一个封装
	$scope.business=function(url,data,success,error,ext){
		//ext为了方便以后拓展使用
		$http.post(url,data).success(function(res){
			console.log(res);
			if(res.code){
				$scope.dialog.success("成功!","一切ok");
				if(success) success(res.data);
				$scope.init();
			}else{
				$scope.dialog.warning("错误!",res.msg);
				if(error) error();
			}
		}).error(function(){$scope.dialog.warning("错误!",'http出错了'); });
	}
	//页面初始化 FIXME
	$scope.init=function(){
		$http.get("/myself/affair/getAll")
		.success(function(res){
			console.log(res);
			$scope.rawData=res;
			if(!$scope.rawData.code){
				$scope.rawAffairs=$scope.rawData.data;
				$scope.affairs=[];
				$scope.dialog.warning("错误",$scope.rawData.msg);
				return;
			}
			//code => value
			$scope.rawAffairs=$scope.rawData.data;
//			console.log($scope.rawAffairs)
			for(var i=0;i<$scope.rawAffairs.length;i++){
				switch($scope.rawAffairs[i].status){
					case '0':
						$scope.rawAffairs[i].statusValue='创建态 ';
						break;
					case '1':
						$scope.rawAffairs[i].statusValue='进行中 ';
						break;
					case '2':
						$scope.rawAffairs[i].statusValue='已完成 ';
						break;
					case '3':
						$scope.rawAffairs[i].statusValue='终止态';
						break;
					default:
						$scope.rawAffairs[i].statusValue='异常';
						break;
				}
			}
			$scope.affairs=$scope.rawAffairs;
			$scope.dialog.success("成功","一切ok");
			console.log($scope.dialog);
		});
		$http.get('/myself/affair/getTypes')
		.success(function(res){
			console.log(res);
			if(res.code){
				$scope.rawTypes=res.data;
			}else{
				$scope.dialog.warning("错误",res.msg);
			}
		});
	}
	$scope.init();
	$scope.setCurId=function(id){
		console.log(id);
		$scope.curId=id;
	}
//	$scope.res='{"code":1,"msg":null,"data":[{"id":1,"what":"bootstrap学习","why":"为了不断的进步","how":"coding","comment":null,"status":"0","createTime":null,"startTime":null,"doneTime":null,"duration":null},{"id":2,"what":"angularjs 学习","why":"全新的数据到view的映射","how":"coding","comment":null,"status":"0","createTime":null,"startTime":null,"doneTime":null,"duration":null}]}';
//	$scope.rawData=JSON.parse($scope.res);
	
	$scope.createFn=function(){
		return $scope.business('/myself/affair/createOne',
				{
			what:$scope.newAffair.what,
			why:$scope.newAffair.why,
			how:$scope.newAffair.how,
			type:$scope.newAffair.type,
			comment:$scope.newAffair.comment
		},function(){$("#create-modal").modal('hide');},
		function(){$("#create-modal").modal('hide');});
	}
	//开始
	$scope.startFn=function(){
		return $scope.business('/myself/affair/startOne',
				{
			id:$scope.curId,
			how:$scope.startAffair.how
		},function(){$("#start-modal").modal('hide');},
		function(){$("#start-modal").modal('hide');});
	}
	//完成
	$scope.completeFn=function(){
		return $scope.business('/myself/affair/completeOne',
				{
			id:$scope.curId,
			comment:$scope.completeAffair.comment
		},function(){$("#complete-modal").modal('hide');},
		function(){$("#complete-modal").modal('hide');});
	}
	//终止
	$scope.terminateFn=function(){
		return $scope.business('/myself/affair/terminateOne',
				{
			id:$scope.curId,
			reason:$scope.terminateAffair.reason
		},function(){$("#terminate-modal").modal('hide');},
		function(){$("#terminate-modal").modal('hide');});
	}
	// 根据状态筛选   FIXME
	$scope.changeStatus=function(code){
		if(code==-1){
			$scope.affairs=$scope.rawAffairs;
			return;
		}
		var arr=new Array();
		for(var i=0;i<$scope.rawAffairs.length;i++){
			if($scope.rawAffairs[i].status==code){
				arr.push($scope.rawAffairs[i]);
			}
		}
		$scope.affairs=arr;
	}
}
)