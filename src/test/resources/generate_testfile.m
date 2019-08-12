offset=100;window = 101;windowC = (window-1)/2;
file = 'testfile.tif';
F = Fast_Tiff_Write(file,1);
IM = zeros([20,20,1000],'uint16');
for ct = 1:size(IM,3)
    IM(:,:,ct)=IM(:,:,ct)+ct;
    F.WriteIMG(IM(:,:,ct));
end
F.close;
med = movmedian(squeeze(IM(1,1,:)),window,'Endpoints','discard');
MED = zeros(size(IM),'uint16');
MED(:,:,1:windowC)=med(1);
MED(:,:,1+windowC:end-windowC)=repmat(reshape(med,[1,1,900]),[20,20]);
MED(:,:,(1+end-windowC):end)=med(end);

file = 'resultfile.tif';
F = Fast_Tiff_Write(file,1);
for ct = 1:size(IM,3)
    IM(:,:,ct)=(offset+IM(:,:,ct))-MED(:,:,ct);
    F.WriteIMG(IM(:,:,ct));
end
F.close;
