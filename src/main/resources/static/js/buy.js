document.querySelectorAll(".btn-buy").forEach(btn => btn.addEventListener("click", async (e) => {
    if (e.target.className.includes("stat-link-active")) {
        location.replace("/bucket")
    } else {
        const productId = +e.target.dataset.id;
        const url = "/bucket/add?productId=" + productId;

        const response = await fetch(url, {
            method: "POST",
        });

        if (response.url.includes("/login")) {
            location.replace(response.url);
        } else {
            e.target.textContent = "В корзине";
            e.target.classList.add("stat-link-active");
        }
    }
}))