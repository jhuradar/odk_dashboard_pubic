angular
    .module('Dashboard')
    .controller('ReportCtrl', ['$rootScope', '$scope', '$attrs', '$http', function ReportCtrl($rootScope, $scope, $attrs, $http) {

    $scope.grouping = {idType: 'Interviewer'};

    $scope.stackChart = {
        type: 'BarChart',
        options: {
          isStacked: true,
          vAxis: { title: 'Interviewer' },
          legend: { position: 'top' }
        },
        data: {
          cols: [
                {id: "t", label: "Interviewer", type: "string"},
                {id: "y", label: "Response", type: "number"},
                {id: "n", label: "Non-response", type: "number"}
          ]
        }
    };

    $scope.loadReport = function() {
      $scope.data = undefined;
      $scope.stackChart.data.rows = [];
      $scope.totalTotal = 0;
      $scope.totalSuccess = 0;

      $http({
          url: $rootScope.links[$attrs.link] + '?idType=' + $scope.grouping.idType,
          method: "GET",
          params: {'nocache': new Date().getTime()}
        }).success(function(data) {
          $scope.data = data;

          $scope.totalTotal = Object.keys(data).map(function(k){
              return +data[k].totalCount;
          }).reduce(function(a,b){ return a + b },0);

          $scope.totalSuccess = Object.keys(data).map(function(k){
              return +data[k].successCount;
          }).reduce(function(a,b){ return a + b },0);

          $scope.stackChart.options.vAxis.title = $scope.grouping.idType;
          $scope.stackChart.data.cols[0].label = $scope.grouping.idType;

          Object.keys(data).map(function(k){
              $scope.stackChart.data.rows.push({ c: [
                  {v: data[k].interviewerNumber},
                  {v: data[k].successCount},
                  {v: data[k].totalCount - data[k].successCount}
              ]});
          });

          $(".table").each(function(index) {
             $(this).stickyTableHeaders({scrollableArea: $(this).parent().parent()});
          });
      });
    }

    $scope.loadReport();

}]);
