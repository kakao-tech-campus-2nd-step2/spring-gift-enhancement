document.addEventListener('DOMContentLoaded', function() {
    // 상품 추가 폼
    if (document.getElementById('product-add-form')) {
        document.getElementById('product-add-form').addEventListener('submit', function(event) {
            handleProductFormSubmit(event, '/api/products', 'POST')
            .then(productId => {
                addOption(productId);
            });
        });
    }

    // 상품 수정 폼
    if (document.getElementById('product-edit-form')) {
        document.getElementById('product-edit-form').addEventListener('submit', function(event) {
            const url = new URL(window.location.href);
            const pathSegments = url.pathname.split('/');
            const productId = pathSegments[pathSegments.length - 1];
            handleProductFormSubmit(event, `/api/products/${productId}`, 'PUT');
        });
    }
});

function handleProductFormSubmit(event, url, method) {
    event.preventDefault();

    const productData = {
        name: document.getElementById('name').value,
        price: document.getElementById('price').value,
        imageUrl: document.getElementById('imageUrl').value,
        categoryId: document.getElementById('categoryId').value
    };

    return fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(productData)
    })
    .then(response => {
        if (response.ok) {
            window.location.href = '/admin/products?page=0&size=5';
            return response.json().then(product => {
                return product.id;
            });
        } else {
            return response.json().then(errorData => {
                throw new Error(errorData.message || '상품을 처리하지 못하였습니다.');
            });
        }
    })
    .catch(error => {
        alert('Error: ' + error.message);
    });
}

function deleteProduct(btn) {
    if(confirm("해당 상품을 삭제하시겠습니까?")) {
        fetch(`/api/products/${btn.dataset.productId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(response => {
            if (response.ok) {
                window.location.href = '/admin/products?page=0&size=5';
            } else {
                return response.json().then(errorData => {
                    throw new Error(errorData.message || '상품을 삭제하지 못하였습니다.');
                });
            }
        })
        .catch(error => {
            alert('Error: ' + error.message);
        });
    }
}

function addOption(productId) {
    const optionData = {
        name: document.getElementById('optionName').value,
        quantity: document.getElementById('optionQuantity').value,
    };

    fetch(`/api/products/${productId}/options`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(optionData)
    })
    .then(response => {
        if (response.ok) {
            location.reload();
        } else {
            return response.json().then(errorData => {
                throw new Error(errorData.message || '옵션을 처리하지 못하였습니다.');
            });
        }
    })
    .catch(error => {
        alert('Error: ' + error.message);
    });
}

function deleteOption(btn) {
    if(confirm("해당 옵션을 삭제하시겠습니까?")) {
        fetch(`/api/products/${btn.dataset.productId}/options/${btn.dataset.optionId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (response.ok) {
                location.reload();
            } else {
                return response.json().then(errorData => {
                    throw new Error(errorData.message || '옵션을 처리하지 못하였습니다.');
                });
            }
        })
        .catch(error => {
            alert('Error: ' + error.message);
        });
    }
}

function editOption(productId, optionId) {
    const button = event.target;
    const row = button.closest('tr');

    const optionData = {
        id: optionId,
        name: row.querySelector('.editedOptionName').value,
        quantity: row.querySelector('.editedOptionQuantity').value,
    };

    fetch(`/api/products/${productId}/options`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(optionData)
    })
    .then(response => {
        if (response.ok) {
            location.reload();
        } else {
            return response.json().then(errorData => {
                throw new Error(errorData.message || '옵션을 처리하지 못하였습니다.');
            });
        }
    })
    .catch(error => {
        alert('Error: ' + error.message);
    });
}