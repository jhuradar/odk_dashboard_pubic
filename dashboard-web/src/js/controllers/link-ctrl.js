angular
    .module('Dashboard')
    .controller('LinkCtrl', ['$rootScope', '$scope', '$attrs', '$http', function LinkCtrl($rootScope, $scope, $attrs, $http) {

    $scope.loadData = function() {
      $scope.totalTotalAny = 0;
      $scope.totalTotalWoman = 0;
      $scope.totalTotalChild = 0;
      $scope.totalTotalHouseholdOnly = 0;
      $http({
          url: $rootScope.links[$attrs.link],
          method: "GET",
          params: {'nocache': new Date().getTime()}
        }).success(function(data) {
          $scope.data = data;

          $scope.totalTotalAny = Object.keys(data).map(function(k){
              return +data[k].totalAny;
          }).reduce(function(a,b){ return a + b },0);

          $scope.totalTotalWoman = Object.keys(data).map(function(k){
              return +data[k].totalWoman;
          }).reduce(function(a,b){ return a + b },0);

          $scope.totalTotalChild = Object.keys(data).map(function(k){
              return +data[k].totalChild;
          }).reduce(function(a,b){ return a + b },0);

          $scope.totalTotalHouseholdOnly = Object.keys(data).map(function(k){
              return +data[k].totalHouseholdOnly;
          }).reduce(function(a,b){ return a + b },0);

          $(".table").each(function(index) {
             $(this).stickyTableHeaders({scrollableArea: $(this).parent().parent()});
          });
      });
    }
}]);
