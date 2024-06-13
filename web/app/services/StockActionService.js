angular.module('app').factory('StockActionService', ['$http', function($http) {
    var apiUrl = 'http://localhost:8080/api/stock-actions';

    return {
        getAll: function() {
            return $http.get(apiUrl);
        },
        restore: function(id) {
            return $http.put(apiUrl + '/restore/' + id);
        }
    };
}]);
