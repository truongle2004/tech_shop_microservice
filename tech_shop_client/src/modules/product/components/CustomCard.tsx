import { useState, useEffect, useRef } from 'react'
import { Link } from 'react-router-dom'
import { formatPriceVND } from '@/utils/formatPrice'
import { featureIcons } from '@/utils/icons'
import type { FC } from 'react'
import type { Product } from '../models'
import { ParsedTags } from '../types'

const CustomCard: FC<Product> = ({ id, slug, title, tags, price, images }) => {
  const [showPlaceholder, setShowPlaceholder] = useState(false)
  const imgRef = useRef<HTMLImageElement>(null)

  const parsedData: ParsedTags = Object.fromEntries(
    tags.split(',').map((item) => {
      const [key, value] = item.split(':')
      return [key.trim(), value.trim()]
    })
  )

  useEffect(() => {
    if (imgRef.current) {
      const img = imgRef.current

      const checkImage = () => {
        // Check if the image has loaded and has non-zero dimensions
        if (
          img.complete &&
          (img.naturalWidth === 0 || img.naturalHeight === 0)
        ) {
          setShowPlaceholder(true)
        }
      }

      // Check immediately after mounting, in case it was already cached
      checkImage()

      // Check again on load in case it wasn't cached
      img.addEventListener('load', checkImage)

      // Clean up the event listener when the component unmounts
      return () => {
        img.removeEventListener('load', checkImage)
      }
    }
  }, [images, title]) // Re-run effect when image source or title change

  if (showPlaceholder) {
    return (
      <div className="card" aria-hidden="true" key="product ">
        <img
          src="/images/placeholder.jpg"
          className="card-img-top"
          alt="Placeholder"
          style={{ height: '190px', objectFit: 'cover' }} // Ensure placeholder has the correct height
        />
        <div className="card-body">
          <h5 className="card-title placeholder-glow">
            <span className="placeholder col-6"></span>
          </h5>
          <p className="card-text placeholder-glow">
            <span className="placeholder col-7"></span>
            <span className="placeholder col-4"></span>
            <span className="placeholder col-4"></span>
            <span className="placeholder col-6"></span>
            <span className="placeholder col-8"></span>
          </p>
          <a
            href="#"
            tabIndex={-1}
            className="btn btn-primary disabled placeholder col-6"
          ></a>
        </div>
      </div>
    )
  }

  return (
    <Link
      to={`/tech_shop/pages/product/${slug}/${id}`}
      className="link-no-decoration"
    >
      <div
        className="card responsive-component"
        style={{ width: '15rem', height: '420px', cursor: 'pointer' }}
        key="main-card"
      >
        <img
          ref={imgRef}
          src={images[0]?.src}
          alt={title}
          className="card-img-top card-img"
          style={{
            height: '190px',
            objectFit: 'cover',
            display: showPlaceholder ? 'none' : 'block'
          }}
          onError={() =>
            console.log(
              'Image Load Failed (onError still handles basic loading issues)'
            )
          }
        />

        <div className="card-body d-flex flex-column" style={{ flex: 1 }}>
          <h5 className="card-title fw-semibold" style={{ fontSize: '0.8rem' }}>
            {title}
          </h5>
          <div className="bg-light overflow-hidden">
            {Object.entries(parsedData).map(([key, value]) => {
              const Icon = featureIcons[key as keyof typeof featureIcons]
              return Icon ? (
                <div
                  key={key}
                  className="d-inline-flex align-items-center"
                  style={{
                    gap: '0.3rem',
                    marginBottom: '1px',
                    marginRight: '0.3rem'
                  }}
                >
                  <Icon size={12} />
                  <span
                    style={{
                      fontSize: '0.7rem',
                      marginRight: '0.3rem',
                      whiteSpace: 'nowrap',
                      overflow: 'hidden',
                      textOverflow: 'ellipsis'
                    }}
                  >
                    {value}
                  </span>
                </div>
              ) : null
            })}
          </div>
        </div>
        <div className="card-footer">
          <p
            className="card-text text-danger fw-semibold mb-0"
            style={{ fontSize: '1rem' }}
          >
            {formatPriceVND(price)}
          </p>
        </div>
      </div>
    </Link>
  )
}

export default CustomCard
