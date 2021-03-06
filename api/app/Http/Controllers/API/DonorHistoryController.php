<?php

namespace App\Http\Controllers\API;

use App\Models\DonorHistory;
use Intervention\Image\Facades\Image;
use Exception;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Storage;

class DonorHistoryController extends BaseController
{
    public function getAllDonorHistories()
    {
        $donorHistory = DonorHistory::get();

        return $this->handleResponse($donorHistory);
    }

    public function getHistoriesByUserId($userId)
    {
        $donorHistory = DonorHistory::where('user_id', $userId)->get();

        return $this->handleResponse($donorHistory);
    }

    public function storeDonorEvidence(Request $request)
    {
        try {
            $image = Image::make(file_get_contents($request->donor_evidence));
            $image->stream();

            $path = '/donors/evidence/' . time() . ".png";

            Storage::disk('local')->put('public/' . $path, $image, 'public');

            $donorHistory = DonorHistory::create([
                'user_id' => $request->user_id,
                'udd' => $request->udd,
                'donor_evidence_path' => '/storage' . $path,
            ]);

            $baseUrl = env("APP_URL", "localhost:8000");
            $donorHistory['donor_evidence_path'] = $baseUrl . $donorHistory['donor_evidence_path'];

            return $this->handleResponse($donorHistory);
        } catch (Exception $e) {
            return $e->getMessage();
        }
    }
}
