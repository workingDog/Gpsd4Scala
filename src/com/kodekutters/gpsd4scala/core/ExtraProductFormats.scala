package com.kodekutters.gpsd4scala.core

import spray.json._

// all this will be removed when the new spray-json is released ..... TODO

// reference: https://github.com/spray/spray-json

/**
 * this extends the JsonFormat of spray-json beyond 15, namely 16-19
 */
trait ExtraProductFormats {
  this: ProductFormats =>

  type JK[T] = JsonFormat[T]   // simple alias for reduced verbosity

  class OptionFormat[T: JK] extends JK[Option[T]] {
    def write(option: Option[T]) = option match {
      case Some(x) => x.toJson
      case None => JsNull
    }

    def read(value: JsValue) = value match {
      case JsNull => None
      case x => Some(x.convertTo[T])
    }
  }

  private def fromField[T](value: JsValue, fieldName: String)(implicit reader: JsonReader[T]) = {
    value match {
      case x: JsObject =>
        var fieldFound = false
        try {
          val fieldValue = x.fields(fieldName)
          fieldFound = true
          reader.read(fieldValue)
        }
        catch {
          case e: NoSuchElementException if !fieldFound =>
            if (reader.isInstanceOf[OptionFormat[_]]) None.asInstanceOf[T]
            else deserializationError("Object is missing required member '" + fieldName + "'", e)
        }
      case _ => deserializationError("Object expected")
    }
  }

  def jsonFormat16[A: JK, B: JK, C: JK, D: JK, E: JK, F: JK, G: JK, H: JK, I: JK, J: JK, K: JK, L: JK, M: JK, N: JK, O: JK, Q: JK, T <: Product : ClassManifest]
  (construct: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, Q) => T): RootJsonFormat[T] = {
    val Array(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, q) = extractFieldNames(classManifest[T])
    jsonFormatx(construct, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, q)
  }

  def jsonFormatx[A: JK, B: JK, C: JK, D: JK, E: JK, F: JK, G: JK, H: JK, I: JK, J: JK, K: JK, L: JK, M: JK, N: JK, O: JK, Q: JK, T <: Product]
  (construct: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, Q) => T, a: String, b: String, c: String, d: String,
   e: String, f: String, g: String, h: String, i: String, j: String, k: String, l: String, m: String, n: String,
   o: String, q: String): RootJsonFormat[T] = new RootJsonFormat[T] {
    def write(p: T) = JsObject(
      productElement2Field[A](a, p, 0,
        productElement2Field[B](b, p, 1,
          productElement2Field[C](c, p, 2,
            productElement2Field[D](d, p, 3,
              productElement2Field[E](e, p, 4,
                productElement2Field[F](f, p, 5,
                  productElement2Field[G](g, p, 6,
                    productElement2Field[H](h, p, 7,
                      productElement2Field[I](i, p, 8,
                        productElement2Field[J](j, p, 9,
                          productElement2Field[K](k, p, 10,
                            productElement2Field[L](l, p, 11,
                              productElement2Field[M](m, p, 12,
                                productElement2Field[N](n, p, 13,
                                  productElement2Field[O](o, p, 14,
                                    productElement2Field[Q](q, p, 15))))))))))))))))
    )

    def read(value: JsValue) = construct(
      fromField[A](value, a),
      fromField[B](value, b),
      fromField[C](value, c),
      fromField[D](value, d),
      fromField[E](value, e),
      fromField[F](value, f),
      fromField[G](value, g),
      fromField[H](value, h),
      fromField[I](value, i),
      fromField[J](value, j),
      fromField[K](value, k),
      fromField[L](value, l),
      fromField[M](value, m),
      fromField[N](value, n),
      fromField[O](value, o),
      fromField[Q](value, q)
    )
  }

  def jsonFormat17[A: JK, B: JK, C: JK, D: JK, E: JK, F: JK, G: JK, H: JK, I: JK, J: JK, K: JK, L: JK, M: JK, N: JK, O: JK, Q: JK, R: JK, T <: Product : ClassManifest]
  (construct: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, Q, R) => T): RootJsonFormat[T] = {
    val Array(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, q, r) = extractFieldNames(classManifest[T])
    jsonFormaty(construct, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, q, r)
  }

  def jsonFormaty[A: JK, B: JK, C: JK, D: JK, E: JK, F: JK, G: JK, H: JK, I: JK, J: JK, K: JK, L: JK, M: JK, N: JK, O: JK, Q: JK, R: JK, T <: Product]
  (construct: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, Q, R) => T, a: String, b: String, c: String, d: String,
   e: String, f: String, g: String, h: String, i: String, j: String, k: String, l: String, m: String, n: String,
   o: String, q: String, r: String): RootJsonFormat[T] = new RootJsonFormat[T] {
    def write(p: T) = JsObject(
      productElement2Field[A](a, p, 0,
        productElement2Field[B](b, p, 1,
          productElement2Field[C](c, p, 2,
            productElement2Field[D](d, p, 3,
              productElement2Field[E](e, p, 4,
                productElement2Field[F](f, p, 5,
                  productElement2Field[G](g, p, 6,
                    productElement2Field[H](h, p, 7,
                      productElement2Field[I](i, p, 8,
                        productElement2Field[J](j, p, 9,
                          productElement2Field[K](k, p, 10,
                            productElement2Field[L](l, p, 11,
                              productElement2Field[M](m, p, 12,
                                productElement2Field[N](n, p, 13,
                                  productElement2Field[O](o, p, 14,
                                    productElement2Field[Q](q, p, 15,
                                      productElement2Field[R](r, p, 16)))))))))))))))))
    )

    def read(value: JsValue) = construct(
      fromField[A](value, a),
      fromField[B](value, b),
      fromField[C](value, c),
      fromField[D](value, d),
      fromField[E](value, e),
      fromField[F](value, f),
      fromField[G](value, g),
      fromField[H](value, h),
      fromField[I](value, i),
      fromField[J](value, j),
      fromField[K](value, k),
      fromField[L](value, l),
      fromField[M](value, m),
      fromField[N](value, n),
      fromField[O](value, o),
      fromField[Q](value, q),
      fromField[R](value, r)
    )
  }


  def jsonFormat18[A: JK, B: JK, C: JK, D: JK, E: JK, F: JK, G: JK, H: JK, I: JK, J: JK, K: JK, L: JK, M: JK, N: JK, O: JK, Q: JK, R: JK, S: JK, T <: Product : ClassManifest]
  (construct: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, Q, R, S) => T): RootJsonFormat[T] = {
    val Array(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, q, r, s) = extractFieldNames(classManifest[T])
    jsonFormatz(construct, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, q, r, s)
  }

  def jsonFormatz[A: JK, B: JK, C: JK, D: JK, E: JK, F: JK, G: JK, H: JK, I: JK, J: JK, K: JK, L: JK, M: JK, N: JK, O: JK, Q: JK, R: JK, S: JK, T <: Product]
  (construct: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, Q, R, S) => T, a: String, b: String, c: String, d: String,
   e: String, f: String, g: String, h: String, i: String, j: String, k: String, l: String, m: String, n: String,
   o: String, q: String, r: String, s: String): RootJsonFormat[T] = new RootJsonFormat[T] {
    def write(p: T) = JsObject(
      productElement2Field[A](a, p, 0,
        productElement2Field[B](b, p, 1,
          productElement2Field[C](c, p, 2,
            productElement2Field[D](d, p, 3,
              productElement2Field[E](e, p, 4,
                productElement2Field[F](f, p, 5,
                  productElement2Field[G](g, p, 6,
                    productElement2Field[H](h, p, 7,
                      productElement2Field[I](i, p, 8,
                        productElement2Field[J](j, p, 9,
                          productElement2Field[K](k, p, 10,
                            productElement2Field[L](l, p, 11,
                              productElement2Field[M](m, p, 12,
                                productElement2Field[N](n, p, 13,
                                  productElement2Field[O](o, p, 14,
                                    productElement2Field[Q](q, p, 15,
                                      productElement2Field[R](r, p, 16,
                                        productElement2Field[S](s, p, 17))))))))))))))))))
    )

    def read(value: JsValue) = construct(
      fromField[A](value, a),
      fromField[B](value, b),
      fromField[C](value, c),
      fromField[D](value, d),
      fromField[E](value, e),
      fromField[F](value, f),
      fromField[G](value, g),
      fromField[H](value, h),
      fromField[I](value, i),
      fromField[J](value, j),
      fromField[K](value, k),
      fromField[L](value, l),
      fromField[M](value, m),
      fromField[N](value, n),
      fromField[O](value, o),
      fromField[Q](value, q),
      fromField[R](value, r),
      fromField[S](value, s)
    )
  }

  def jsonFormat19[A: JK, B: JK, C: JK, D: JK, E: JK, F: JK, G: JK, H: JK, I: JK, J: JK, K: JK, L: JK, M: JK, N: JK, O: JK, Q: JK, R: JK, S: JK, U: JK, T <: Product : ClassManifest]
  (construct: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, Q, R, S, U) => T): RootJsonFormat[T] = {
    val Array(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, q, r, s, u) = extractFieldNames(classManifest[T])
    jsonFormatu(construct, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, q, r, s, u)
  }

  def jsonFormatu[A: JK, B: JK, C: JK, D: JK, E: JK, F: JK, G: JK, H: JK, I: JK, J: JK, K: JK, L: JK, M: JK, N: JK, O: JK, Q: JK, R: JK, S: JK, U: JK, T <: Product]
  (construct: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, Q, R, S, U) => T, a: String, b: String, c: String, d: String,
   e: String, f: String, g: String, h: String, i: String, j: String, k: String, l: String, m: String, n: String,
   o: String, q: String, r: String, s: String, u: String): RootJsonFormat[T] = new RootJsonFormat[T] {
    def write(p: T) = JsObject(
      productElement2Field[A](a, p, 0,
        productElement2Field[B](b, p, 1,
          productElement2Field[C](c, p, 2,
            productElement2Field[D](d, p, 3,
              productElement2Field[E](e, p, 4,
                productElement2Field[F](f, p, 5,
                  productElement2Field[G](g, p, 6,
                    productElement2Field[H](h, p, 7,
                      productElement2Field[I](i, p, 8,
                        productElement2Field[J](j, p, 9,
                          productElement2Field[K](k, p, 10,
                            productElement2Field[L](l, p, 11,
                              productElement2Field[M](m, p, 12,
                                productElement2Field[N](n, p, 13,
                                  productElement2Field[O](o, p, 14,
                                    productElement2Field[Q](q, p, 15,
                                      productElement2Field[R](r, p, 16,
                                        productElement2Field[S](s, p, 17,
                                          productElement2Field[S](s, p, 18)))))))))))))))))))
    )

    def read(value: JsValue) = construct(
      fromField[A](value, a),
      fromField[B](value, b),
      fromField[C](value, c),
      fromField[D](value, d),
      fromField[E](value, e),
      fromField[F](value, f),
      fromField[G](value, g),
      fromField[H](value, h),
      fromField[I](value, i),
      fromField[J](value, j),
      fromField[K](value, k),
      fromField[L](value, l),
      fromField[M](value, m),
      fromField[N](value, n),
      fromField[O](value, o),
      fromField[Q](value, q),
      fromField[R](value, r),
      fromField[S](value, s),
      fromField[U](value, u)
    )
  }

}
