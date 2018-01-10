/**
 * Created by MisakaMikoto on 2017. 12. 21..
 */
app.controller('GameController', ['$scope', '$location', '$log', 'GameService',
    function ($scope, $location, $log, GameService) {
        $scope.formData = new FormData();

        angular.element(document).ready(function() {
            let gallery = angular.element(document.querySelector('#gallery'));
            if(gallery.length > 0) {
                let getUploadListPromise = GameService.getUploadList();
                getUploadListPromise.then(function success(response) {
                    let group = 0;
                    for (let i = 0; i < response[0].data.length; i++) {
                        let left = 0;
                        let top = 0;

                        if (i % 3 == 0) {
                            ++group;

                        }

                        if (group > 0) {
                            left = ((i % 3) * 30);
                            top = ((group - 1) * 400);
                        }

                        response[0].data[i].group = group;
                        response[0].data[i].style = {
                            'position': 'absolute',
                            'left': left + '%',
                            'top': top + 'px',
                        };
                    }
                    $scope.videos = response[0].data;
                    angular.element(document.querySelector('#gallery'))[0].style.height = (((response[0].data.length * 400) / 3) + 400) + 'px';

                }).catch(function (error) {
                    $log.error('ERROR: ', error);
                    throw error;
                });

            } else {
                $('#file').on("change", function(){
                    setVideoInformation($(this));
                    setVideoPreview($(this))
                });
            }

            let myModal = $('#myModal');
            if(myModal.length > 0) {
                myModal.on('hidden.bs.modal', function () {
                    // angular.element(document.querySelector('#myModal').querySelector('.modal-title')).html(subject);
                    let iframe = angular.element(document.querySelector('#myModal').querySelector('.modal-body').getElementsByTagName('iframe'));
                    iframe.removeAttr('src');
                });
            }
        });

        $scope.upload = function() {
            let formData = new FormData(angular.element(document.querySelector('#uploadForm'))[0]);
            GameService.upload(formData);
        };

        $scope.popupOpen = function(subject, videoId) {
            // angular.element(document.querySelector('#myModal').querySelector('.modal-title')).html(subject);
            let iframe = angular.element(document.querySelector('#myModal').querySelector('.modal-body').getElementsByTagName('iframe'));
            iframe.attr('src', 'https://www.youtube.com/embed/' + videoId);
        };

        function setVideoInformation(file) {
            angular.element(document.querySelector('#uploadFileName')).html("파일 이름: " + file[0].files[0].name);

            let fileSize = file[0].files[0].size;
            let fileSizeMb = fileSize / (1000 * 1000).toFixed(2);
            let fileSizeGb = (fileSizeMb / 1000).toFixed(2);
            fileSizeMb < 1024 ? angular.element(document.querySelector('#uploadFileSize')).html("파일 크기 : " + fileSizeMb + " (MB)") :
                angular.element(document.querySelector('#uploadFileSize')).html("파일 크기 : " + fileSizeGb + " (GB)");
        };

        function setVideoPreview(file) {
            var fileURL = URL.createObjectURL(file[0].files[0]);
            angular.element(document.querySelector('#video'))[0].src = fileURL;
        };
    }
]);


app.controller('GamePopupController', ['$scope', '$modalInstance', 'title',
    function ($scope, $modalInstance, title) {
        $scope.title = title;
        $scope.close = function () {
            $modalInstance.dismiss('cancel');
        };
    }
]);