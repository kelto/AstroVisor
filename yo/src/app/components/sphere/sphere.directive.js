/*global THREE, THREEx*/
(function(){
  'use strict';

  angular.module('yo').directive('asSphere', sphere);

  /** @ngInject */
  function sphere($document, $log, systems){
    var directive = {
      restrict:'A',
      require:'^asMap',
      link:linkFunc
    };

    var baseURL = 'images/textures/';
    var docSize = 90*$document.height()/100;
    var standardWidth = docSize, standardHeight = docSize;
    var sizeEnum = {
      XS:0.3,
      SMALL:0.4,
      MEDIUM:0.5,
      NORMAL:0.6,
      LARGE:0.8,
      XL:0.85,
      XXL:0.95,
      XXXL:1
    };

    return directive;

    function linkFunc(scope, el, attr, vm){
      var planetId = attr.id;
      var planet = systems.getPlanetByCodeName(planetId);
      el.append(renderer(planet));

      vm.newRenderedPlanet();
    }

    function renderer(data){
      $log.debug(data);

      var width = sizeEnum[data.size]*standardWidth;
      var height = sizeEnum[data.size]*standardHeight;

      var renderer = new THREE.WebGLRenderer({
        antialias: true,
        alpha:true
      });
      renderer.setSize(width, height);
      renderer.shadowMapEnabled = true;

      var value = 20;
      if(data.rings){
        value = 30;
      }

      var updateFcts = [];
      var scene = new THREE.Scene();
      var camera = new THREE.PerspectiveCamera(value, width/height, 0.01, 100);
      camera.position.z = 3;

      //var light = new THREE.AmbientLight(0x888888);
      var light = new THREE.AmbientLight(0xcccccc, 1);
      scene.add(light);
      scene.add(createDirectionalLight());

      var planet = createPlanetFromTexture(data.texture, data.type);
      scene.add(planet);
      updateFcts.push(function (delta) {
        planet.rotation.y += data.orbit.orbital_speed/20*delta;
      });

      if(data.atmosphere){
        var atmosphere = createAtmosphere();
        scene.add(atmosphere);
        updateFcts.push(function (delta) {
          atmosphere.rotation.y += data.orbit.orbital_speed/8*delta;
        });
      }
      else if(data.rings){
        var mesh = createRings(data.texture);
        mesh.receiveShadow = true;
        mesh.castShadow = true;
        scene.add(mesh);
      }

      updateFcts.push(function () {
        renderer.render(scene, camera);
      });

      var lastTimeMsec = null;
      requestAnimationFrame(function animate(nowMsec) {
        // keep looping
        requestAnimationFrame(animate);
        // measure time
        lastTimeMsec = lastTimeMsec || nowMsec - 1000 / 60;
        var deltaMsec = Math.min(200, nowMsec - lastTimeMsec);
        lastTimeMsec = nowMsec;
        // call each update function
        updateFcts.forEach(function (updateFn) {
          updateFn(deltaMsec / 1000, nowMsec / 1000);
        });
      });

      return renderer.domElement;
    }

    function createDirectionalLight(){
      var light = new THREE.DirectionalLight(0x555555, 1);
      light.position.set(5, 5, 5);
      light.castShadow = true;
      light.shadowCameraNear = 0.01;
      light.shadowCameraFar = 15;
      light.shadowCameraFov = 45;
      light.shadowCameraLeft = -1;
      light.shadowCameraRight = 1;
      light.shadowCameraTop = 1;
      light.shadowCameraBottom = -1;
      light.shadowBias = 0.001;
      light.shadowDarkness = 0.2;
      light.shadowMapWidth = 1024;
      light.shadowMapHeight = 1024;

      return light;
    }

    function createPlanetFromTexture(texture, type){
      var geometry	= new THREE.SphereGeometry(0.5, 32, 32);
      var meshPhongMaterial = {
        map	: THREE.ImageUtils.loadTexture(baseURL+texture+'.jpg'),
        bumpScale: 0.005
      };

      if(type === 'telluric'){
        meshPhongMaterial['bumpMap'] = THREE.ImageUtils.loadTexture(baseURL+texture+'_bump.jpg');
      }

      var material	= new THREE.MeshPhongMaterial(meshPhongMaterial);
      var mesh	= new THREE.Mesh(geometry, material);
      return mesh;
    }

    function createAtmosphere(){
      var canvasResult	= angular.element('<canvas></canvas>').get(0);
      canvasResult.width	= 1024;
      canvasResult.height	= 512;
      var contextResult	= canvasResult.getContext('2d');

      var imageMap	= new Image();
      imageMap.addEventListener("load", function() {

        // create dataMap ImageData for earthcloudmap
        var canvasMap	= angular.element('<canvas></canvas>').get(0);
        canvasMap.width	= imageMap.width;
        canvasMap.height= imageMap.height;
        var contextMap	= canvasMap.getContext('2d');
        contextMap.drawImage(imageMap, 0, 0);
        var dataMap	= contextMap.getImageData(0, 0, canvasMap.width, canvasMap.height);

        // load earthcloudmaptrans
        var imageTrans	= new Image();
        imageTrans.addEventListener("load", function(){
          // create dataTrans ImageData for earthcloudmaptrans
          var canvasTrans		= angular.element('<canvas></canvas>').get(0);
          canvasTrans.width	= imageTrans.width;
          canvasTrans.height	= imageTrans.height;
          var contextTrans	= canvasTrans.getContext('2d');
          contextTrans.drawImage(imageTrans, 0, 0);
          var dataTrans		= contextTrans.getImageData(0, 0, canvasTrans.width, canvasTrans.height);
          // merge dataMap + dataTrans into dataResult
          var dataResult		= contextMap.createImageData(canvasMap.width, canvasMap.height);
          for(var y = 0, offset = 0; y < imageMap.height; y++){
            for(var x = 0; x < imageMap.width; x++, offset += 4){
              dataResult.data[offset+0]	= dataMap.data[offset+0];
              dataResult.data[offset+1]	= dataMap.data[offset+1];
              dataResult.data[offset+2]	= dataMap.data[offset+2];
              dataResult.data[offset+3]	= 255 - dataTrans.data[offset+0];
            }
          }
          // update texture with result
          contextResult.putImageData(dataResult,0,0);
          material.map.needsUpdate = true;
        });
        imageTrans.src = baseURL+'/cloudmaptrans.jpg';
      }, false);
      imageMap.src = baseURL+'/cloudmap.jpg';

      var geometry	= new THREE.SphereGeometry(0.51, 32, 32);
      var material	= new THREE.MeshPhongMaterial({
        map		: new THREE.Texture(canvasResult),
        side		: THREE.DoubleSide,
        transparent	: true,
        opacity		: 0.8
      });
      var mesh	= new THREE.Mesh(geometry, material);
      return mesh;
    }

    function createRings(texture){
      // create destination canvas
      var canvasResult	= angular.element('<canvas></canvas>').get(0);
      canvasResult.width	= 915;
      canvasResult.height	= 64;
      var contextResult	= canvasResult.getContext('2d');

      // load earthcloudmap
      var imageMap	= new Image();
      imageMap.addEventListener("load", function() {

        // create dataMap ImageData for earthcloudmap
        var canvasMap	= angular.element('<canvas></canvas>').get(0);
        canvasMap.width	= imageMap.width;
        canvasMap.height= imageMap.height;
        var contextMap	= canvasMap.getContext('2d');
        contextMap.drawImage(imageMap, 0, 0);
        var dataMap	= contextMap.getImageData(0, 0, canvasMap.width, canvasMap.height);

        // load earthcloudmaptrans
        var imageTrans	= new Image();
        imageTrans.addEventListener("load", function(){
          // create dataTrans ImageData for earthcloudmaptrans
          var canvasTrans	= angular.element('<canvas></canvas>').get(0);
          canvasTrans.width	= imageTrans.width;
          canvasTrans.height = imageTrans.height;
          var contextTrans = canvasTrans.getContext('2d');
          contextTrans.drawImage(imageTrans, 0, 0);
          var dataTrans	= contextTrans.getImageData(0, 0, canvasTrans.width, canvasTrans.height);
          // merge dataMap + dataTrans into dataResult
          var dataResult = contextMap.createImageData(canvasResult.width, canvasResult.height);
          for(var y = 0, offset = 0; y < imageMap.height; y++){
            for(var x = 0; x < imageMap.width; x++, offset += 4){
              dataResult.data[offset+0]	= dataMap.data[offset+0];
              dataResult.data[offset+1]	= dataMap.data[offset+1];
              dataResult.data[offset+2]	= dataMap.data[offset+2];
              dataResult.data[offset+3]	= 255 - dataTrans.data[offset+0]/4;
            }
          }
          // update texture with result
          contextResult.putImageData(dataResult, 0, 0);
          material.map.needsUpdate = true;
        });
        imageTrans.src = baseURL+texture+'_ringpattern.gif';
      }, false);
      imageMap.src = baseURL+texture+'_ringcolor.jpg';

      var geometry = new THREEx.Planets._RingGeometry(0.55, 0.75, 64);
      var material = new THREE.MeshPhongMaterial({
        map:new THREE.Texture(canvasResult),
        side:THREE.DoubleSide,
        transparent:true,
        opacity:0.8
      });
      var mesh = new THREE.Mesh(geometry, material);
      mesh.lookAt(new THREE.Vector3(0.5,-4,1));
      return mesh;
    }
  }
})();
