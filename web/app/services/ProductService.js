angular.module('app').factory('ProductService', ['$http', function($http) {
    var apiUrl = 'http://localhost:8080/api/products'; // Corrigir a porta aqui

    return {
        getAll: function() {
            return $http.get(apiUrl);
        },
        getById: function(id) {
            return $http.get(apiUrl + '/' + id);
        },
        create: function(product) {
            return $http.post(apiUrl, product);
        },
        update: function(id, product) {
            return $http.put(apiUrl + '/' + id, product);
        },
        delete: function(id) {
            return $http.delete(apiUrl + '/' + id);
        },
        search: function(name) {
            return $http.get(apiUrl + '/search', { params: { name: name } });
        }
    };
}]);
