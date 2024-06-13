var app = angular.module('inventoryApp', []);

app.controller('StockActionController', ['$scope', '$http', function($scope, $http) {
    console.log("StockActionController initialized");

    $scope.fetchStockActions = function() {
        console.log("Fetching stock actions");
        $http.get('http://localhost:8080/api/stock-actions')
            .then(function(response) {
                $scope.stockActions = response.data;
            }, function(error) {
                console.error('Error fetching stock actions:', error);
            });
    };

    $scope.fetchStockActions();
}]);
