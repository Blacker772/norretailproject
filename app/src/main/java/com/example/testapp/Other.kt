package com.example.testapp


//        if (intent.resolveActivity(context.packageManager) != null) {
//            context.startActivity(intent)
//        } else {
//            // Предложить установить Яндекс Карты или открыть в браузере
//            val browserUri = "https://yandex.ru/maps/?rtext=$rText&rtt=auto"
//            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(browserUri))
//            context.startActivity(browserIntent)
//        }

//        val shops = point.map { Points(it.latitude, it.longitude) }
//        val rText = shops.joinToString("~"){"${it.latitude},${it.longitude}"}
//        val uri = "yandexmaps://maps.yandex.ru/?rtext=$rText&rtt=auto"
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri)).apply {
//            `package` = "ru.yandex.yandexmaps"
//        }

//    private lateinit var geoObjectTapListener: GeoObjectTapListener
//        MapKitFactory.initialize(requireContext())

////        //Инициализация карты
////        val mapView = binding?.yandexMap
////
////        //Фокус карты на Норильск
////        mapView?.mapWindow?.map?.move(
////            CameraPosition(
////                Point(69.3558, 88.1893),
////                13f,
////                0f,
////                0f
////            )
////        )
////
////        //Слушатель карты
////        geoObjectTapListener = GeoObjectTapListener {
////            val dialog = BottomSheetDialog(requireContext())
////            val parent = requireActivity().findViewById<ViewGroup>(android.R.id.content)
////            val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_object, parent, false)
////            dialog.setContentView(dialogView)
////
////            val tvName = dialogView.findViewById<TextView>(R.id.tvObjectName)
////            val tvDescription = dialogView.findViewById<TextView>(R.id.tvObjectAddress)
////            val btClose = dialogView.findViewById<Button>(R.id.closeObjectButton)
////
////            tvName.text = it.geoObject.name
////            dialog.show()
////
////            btClose.setOnClickListener {
////                dialog.dismiss()
////            }
////            true
////        }
////
////        mapView?.mapWindow?.map?.addTapListener(geoObjectTapListener)
////    }
////
////    override fun onStart() {
////        super.onStart()
////        MapKitFactory.getInstance().onStart()
////        binding?.yandexMap?.onStart()
////    }
////
////    override fun onStop() {
////        binding?.yandexMap?.onStop()
////        MapKitFactory.getInstance().onStop()
////        super.onStop()
////    }
////
////    override fun onDestroyView() {
////        super.onDestroyView()
////        geoObjectTapListener.let { binding?.yandexMap?.mapWindow?.map?.removeTapListener(it) }
////        binding = null