(function() {
    "use strict";
    const API_URL = "/bin/movies/search";
    const main = document.getElementById('main');
    const form = document.getElementById('form');
    const search = document.getElementById('search');
    const overlay = document.getElementById('overlay');
    const overlayContent = document.getElementById('overlay-content');

    function onDocumentReady() {
    const searchTerm = search.value;
            if (searchTerm) {
        getMovies(API_URL);
        }
    }

    function getMovies(url) {
        fetch(url).then(res => res.json()).then(data => {

            if (data.length !== 0) {
                showMovies(data);
            } else {
                main.innerHTML = `<h1 class="no-results">No Results Found</h1>`
            }

        })
    }

    function showMovies(data) {
        main.innerHTML = '';

        data.forEach(movie => {
            const {
                title,
                imageUrl,
                description,
                youTubeId
            } = movie;
            const movieEl = document.createElement('div');
            movieEl.classList.add('movie-tile');
            movieEl.innerHTML = `
             <img src="${imageUrl}" alt="${title}">

            <div class="movie-tile-info">
                <h3>${title}</h3>
            </div>

            <div class="overview">

                <h3>Overview</h3>
                ${description}
                </br>
                <button class="watch-trailer" id="${youTubeId}">Watch Trailer</button>
            </div>
        `
            main.appendChild(movieEl);
            document.getElementById(youTubeId).addEventListener('click', () => {
                      openOverlay(movie)
                    })
        })
    }

    document.getElementById('closebtn').addEventListener('click', () =>{
    closeOverlay();
    })

    function closeOverlay() {
        overlay.style.width = "0%";
    }

    function openOverlay(movie) {
        let videoTitle = movie.title;
        let id = movie.youTubeId

         overlay.style.width = "100%";
         var embed = [];

         embed.push(`
              <iframe width="560" height="315" src="https://www.youtube.com/embed/${id}"
              title="YouTube video player" class="embed hide" frameborder="0"
              allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen>
              </iframe>
         `)

        var content = `
        <h1 class="no-results">${videoTitle}</h1>
        <br/>

        ${embed.join('')}
        <br/>
        `
        overlayContent.innerHTML = content;
        showVideos();

    }

    var activeSlide = 0;
    var totalVideos = 0;
    function showVideos(){
        let embedClasses = document.querySelectorAll('.embed');

        totalVideos = embedClasses.length;
        embedClasses.forEach((embedTag, idx) => {
             if(activeSlide == idx){
            embedTag.classList.add('show')
            embedTag.classList.remove('hide')
            }else{
            embedTag.classList.add('hide');
            embedTag.classList.remove('show')
            }
        })
    }

    if (document.readyState !== "loading") {
        onDocumentReady();
    } else {
        document.addEventListener("DOMContentLoaded", onDocumentReady);
    }
    form.addEventListener('submit', (e) => {
        e.preventDefault();
        const searchTerm = search.value;
        const pageResult = form.dataset.results;
        if (searchTerm) {
            getMovies(API_URL + '?query=' + searchTerm +'&results='+pageResult)
        }
    })
}());