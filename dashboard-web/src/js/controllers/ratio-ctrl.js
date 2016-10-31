angular
    .module('Dashboard')
    .controller('RatioCtrl', ['$rootScope', '$scope', '$attrs', '$http', function RatioCtrl($rootScope, $scope, $attrs, $http) {

    $scope.grouping = {idType: 'Interviewer'};

    $scope.loadRatio = function() {
      $scope.data = undefined;
      $scope.totalCount1 = 0;
      $scope.totalCount2 = 0;
      $http({
          url: $rootScope.links[$attrs.link] + '?idType=' + $scope.grouping.idType,
          method: "GET",
          params: {'nocache': new Date().getTime()}
        }).success(function(data) {
          $scope.data = data;

          $scope.totalCount1 = Object.keys(data).map(function(k){
              return +data[k].count1;
          }).reduce(function(a,b){ return a + b },0);

          $scope.totalCount2 = Object.keys(data).map(function(k){
              return +data[k].count2;
          }).reduce(function(a,b){ return a + b },0);

          $(".table").each(function(index) {
             $(this).stickyTableHeaders({scrollableArea: $(this).parent().parent()});
          });
      });
    }

    $scope.loadRatio();
}]);
