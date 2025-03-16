import useCategoryStore from '@/store/categoryStore'
import { formatPriceVND } from '@/utils/formatPrice'
import { scrollToTop } from '@/utils/scrollToTop'
import { useQuery } from '@tanstack/react-query'
import { useEffect, useState } from 'react'
import Button from 'react-bootstrap/Button' // Keep this for now
import { FaStar } from 'react-icons/fa'
import { Link, useParams } from 'react-router-dom'
import { getProductAPI, getProductDetailByIdAPI } from '../services'

const DEFAULT_PAGE_NO = 1
const DEFAULT_PAGE_SIZE = 20

const DetailUI = () => {
  const { productId } = useParams()
  const [imageIndex, setImageIndex] = useState(0)
  const { listCategory } = useCategoryStore()
  const [quantity, setQuantity] = useState(1)

  // call api get detail product by id
  const {
    data: detailData,
    isLoading: detailIsLoading,
    isError: detailIsError,
    error: detailError
  } = useQuery({
    queryKey: ['detail', productId],
    queryFn: () => getProductDetailByIdAPI({ id: Number(productId) }),
    enabled: !!productId
  })

  // after call product detail success, get more recommend product by category slug
  const productCategorySlug = listCategory
    .filter((item) => item.name === detailData?.category)
    .at(0)?.slug

  // call api get more recommend product
  const {
    data: products,
    isLoading: productsIsLoading,
    isError: productsIsError,
    error: productsError
  } = useQuery({
    queryKey: [
      'products',
      DEFAULT_PAGE_NO,
      DEFAULT_PAGE_SIZE,
      productCategorySlug
    ],
    queryFn: () =>
      getProductAPI({
        pageNo: DEFAULT_PAGE_NO,
        pageSize: DEFAULT_PAGE_SIZE,
        slug: productCategorySlug as string
      }),
    enabled: !!productCategorySlug
  })

  // const { mutateAsync: addProductToCartMutation } = useMutation({
  //   mutationFn: addProductToCartAPI,
  //   onSuccess: (data) => {
  //     ToastifySuccess(data.message)
  //   }
  // })

  const handleClickAddToCart = (e) => {
    e.preventDefault()
    handleAddToCart()
  }

  const handleAddToCart = async () => {
    // try {
    //   if (id) {
    //     await addProductToCartMutation({
    //       productId: Number(productId),
    //       quantity,
    //       userId: id as string
    //     })
    //   } else {
    //     await getUserId()
    //   }
    // } catch (err) {
    //   console.error('Error adding to cart:', err)
    // }
  }

  const reduceQuantity = () => {
    setQuantity((prev) => (prev > 1 ? prev - 1 : 1))
  }

  const increaseQuantity = () => {
    setQuantity((prev) => prev + 1)
  }

  useEffect(() => {
    scrollToTop()
  }, [productId])

  const clickImage = (index: number) => {
    setImageIndex(index)
  }

  if (detailIsLoading || productsIsLoading) {
    return (
      <div
        className="d-flex justify-content-center align-items-center"
        style={{ height: '200px' }}
      >
        <div>Loading...</div>
      </div>
    )
  }

  if (detailIsError || productsIsError) {
    return (
      <div style={{ color: 'red', marginTop: '10px', textAlign: 'center' }}>
        Error: {detailIsError ? detailError?.message : productsError?.message}
      </div>
    )
  }

  return (
    <div className="container mt-5 py-4 px-xl-5" style={{ width: '80%' }}>
      <div className="row mb-4">
        {/* Image Scroller (Left Side) */}
        <div className="d-none d-lg-block col-lg-1">
          <div
            className="image-vertical-scroller"
            style={{
              maxHeight: '500px',
              overflowY: 'auto',
              overflowX: 'hidden'
            }}
          >
            <div className="d-flex flex-column">
              {detailData?.images?.map((image, index) => (
                <img
                  key={image.id}
                  src={image.src}
                  alt={image.alt}
                  onClick={() => clickImage(index)}
                  className={`cover rounded mb-2 ${imageIndex === index ? 'border border-primary' : ''}`} // Dynamic border based on selected image
                  style={{
                    width: '40px',
                    height: '40px',
                    objectFit: 'cover',
                    cursor: 'pointer',
                    opacity: index === imageIndex ? 1 : 0.7,
                    transition: 'opacity 0.2s ease-in-out' // Smooth opacity transition
                  }}
                />
              ))}
            </div>
          </div>
        </div>

        {/* Main Image (Center) */}
        <div className="col-lg-6">
          <div className="row">
            <div className="col-12 mb-4">
              <img
                src={detailData?.images?.[imageIndex]?.src}
                alt={detailData?.images?.[imageIndex]?.alt || 'Product Image'}
                className="cover rounded"
                style={{
                  width: '100%',
                  height: '100%',
                  objectFit: 'cover',
                  borderRadius: '8px'
                }}
              />
            </div>
          </div>
        </div>

        {/* Product Information (Right Side) */}
        <div className="col-lg-5">
          <div className="d-flex flex-column h-100">
            <h2 className="mb-1">{detailData?.title}</h2>
            <h4 className="text-danger">
              <strong>{formatPriceVND(detailData?.price as number)}</strong>
            </h4>

            {/* Add to Cart and Buy Now Buttons */}
            <div className="row g-3 mb-4">
              <div className="col">
                <button
                  type="button"
                  className="btn btn-outline-dark py-2 w-100"
                  onClick={handleClickAddToCart}
                >
                  Add to cart
                </button>
              </div>
              <div className="col">
                <button className="btn btn-dark py-2 w-100">Buy now</button>
              </div>
            </div>

            {/* Quantity Adjustment */}
            <div className="d-flex align-items-center gap-2 mb-4">
              <Button
                className="btn btn-primary"
                size="sm"
                onClick={reduceQuantity}
                aria-label="Decrease quantity"
              >
                -
              </Button>
              <p className="m-0 fw-bold">{quantity}</p>
              <Button
                className="btn btn-primary"
                size="sm"
                onClick={increaseQuantity}
                aria-label="Increase quantity"
              >
                +
              </Button>
            </div>

            {/* Product Details */}
            <h4 className="mb-0">Details</h4>
            <hr />
            <dl className="row">
              <dt className="col-sm-4">Category</dt>
              <dd className="col-sm-8 mb-3">{detailData?.category}</dd>

              <dt className="col-sm-4">Brand</dt>
              <dd className="col-sm-8 mb-3">{detailData?.vendor}</dd>

              <dt className="col-sm-4">Status</dt>
              <dd className="col-sm-8 mb-3">
                {detailData?.available ? 'In stock' : 'Out of stock'}
              </dd>
            </dl>
          </div>
        </div>
      </div>

      {/* Description and Related Products */}
      <div style={{ display: 'flex' }}>
        {/* Description */}
        {detailData?.description && (
          <section style={{ width: '70%', marginBottom: '20px' }}>
            <h4 className="mb-0 text-center">Description</h4>
            <hr />
            <div className="bg-light">
              <p
                className="lead link-no-decoration"
                style={{ whiteSpace: 'pre-wrap', padding: '10px 20px' }}
                dangerouslySetInnerHTML={{ __html: detailData?.description }}
              />
            </div>
          </section>
        )}

        {/* Related Products */}
        <aside
          style={{
            display: 'flex',
            flexDirection: 'column',
            width: '30%',
            paddingLeft: '20px'
          }}
        >
          <h5>Có thể bạn quan tâm</h5>
          <div>
            {products?.content?.map((item, index) => {
              if (!item || index >= 6) return null

              return (
                <Link
                  to={`/tech_shop/pages/product/${item.slug}/${item.id}`}
                  key={item.id}
                  className="link-no-decoration"
                >
                  <div
                    className="d-flex gap-3 mb-2 p-2"
                    style={{
                      cursor: 'pointer',
                      borderRadius: '4px',
                      transition: 'background-color 0.2s ease-in-out'
                    }}
                    onMouseEnter={(e) =>
                      (e.currentTarget.style.backgroundColor = '#f8f9fa')
                    }
                    onMouseLeave={(e) =>
                      (e.currentTarget.style.backgroundColor = 'transparent')
                    }
                  >
                    <img
                      src={item.images[0].src}
                      alt={item.images[0].alt}
                      style={{
                        width: '80px',
                        height: '80px',
                        objectFit: 'contain'
                      }}
                    />
                    <div>
                      <p style={{ fontSize: '14px', fontWeight: 'bold' }}>
                        {item.title}
                      </p>
                      <p className="text-danger">
                        <strong>{formatPriceVND(item.price)}</strong>
                      </p>
                    </div>
                  </div>
                </Link>
              )
            })}
          </div>
        </aside>
      </div>

      {/* Ratings and Reviews */}
      <section className="mt-4">
        <h4 className="mb-3">Đánh giá & Nhận xét {detailData?.title}</h4>
        <div className="d-flex justify-content-center">
          <div className="content text-center">
            <div className="ratings">
              <span className="product-rating">4.6</span>
              <span>/5</span>
              <div className="stars">
                {Array(5)
                  .fill(null)
                  .map((_, index) => (
                    <FaStar
                      key={index}
                      color={index < 4 ? '#FFD700' : '#ddd'}
                      size={20}
                    />
                  ))}
              </div>
              <div className="rating-text">
                <span>46 ratings & 15 reviews</span>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  )
}

export default DetailUI
